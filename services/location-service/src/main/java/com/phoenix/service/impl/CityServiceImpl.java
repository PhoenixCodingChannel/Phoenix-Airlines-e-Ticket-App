package com.phoenix.service.impl;

import com.phoenix.mapper.CityMapper;
import com.phoenix.model.City;
import com.phoenix.payload.request.CityRequest;
import com.phoenix.payload.response.CityResponse;
import com.phoenix.repository.CityRepository;
import com.phoenix.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest cityRequest) throws Exception {
        if(cityRepository.existsByCityCode(cityRequest.getCityCode())){
            throw new Exception("City with the given code already exists");
        }

        City city = CityMapper.toEntity(cityRequest);
        City result = cityRepository.save(city);
        return CityMapper.toResponse(result);
    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
         City city = cityRepository.findById(id)
                 .orElseThrow(() -> new Exception("City not found"));

         return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) throws Exception {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new Exception("City not found"));

        if(cityRepository.existsByCityCode(cityRequest.getCityCode())){
            throw new Exception("City with the given code already exists");
        }
        City updatedCity = cityRepository.save(CityMapper.updateEntity(city, cityRequest));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new Exception("City not found"));

        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getAllCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findCountryCodeIgnoreCase(countryCode, pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

}
