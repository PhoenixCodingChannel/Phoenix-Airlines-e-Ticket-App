package com.phoenix.service;

import com.phoenix.payload.request.AirportRequest;
import com.phoenix.payload.response.AirportResponse;

import java.util.List;

public interface AirpostService {

    AirportResponse createAirport(AirportRequest request) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;

    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(Long id, AirportRequest request) throws Exception;
    void deleteAirport(Long id) throws Exception;
    List<AirportResponse> getAirportByCityId(Long cityId);
}
