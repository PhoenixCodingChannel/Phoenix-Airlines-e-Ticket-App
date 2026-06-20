package com.phoenix.service.impl;

import com.phoenix.mapper.AirportMapper;
import com.phoenix.model.Airport;
import com.phoenix.model.City;
import com.phoenix.payload.request.AirportRequest;
import com.phoenix.payload.response.AirportResponse;
import com.phoenix.repository.AirportRepository;
import com.phoenix.repository.CityRepository;
import com.phoenix.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {

        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with IATA code already exists");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new Exception("City not found"));
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found"));

        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport with the given id: "+id+" does not exist"));

        if (request.getIataCode() != null
                && !request.getIataCode().equals(existingAirport.getIataCode())
                && airportRepository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with the given IATA code already exists");
        }
        AirportMapper.updateEntity(request, existingAirport);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found"));
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
