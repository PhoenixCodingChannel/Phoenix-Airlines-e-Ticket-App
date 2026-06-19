package com.phoenix.mapper;

import com.phoenix.model.Airport;
import com.phoenix.payload.request.AirportRequest;
import com.phoenix.payload.response.AirportResponse;

public class AirportMapper {

    public static Airport toEntity(AirportRequest airportRequest){
        if(airportRequest == null) return null;

        return Airport.builder()
                .iataCode(airportRequest.getIataCode())
                .name(airportRequest.getName())
//                .timeZone(airportRequest.getTimeZone())
                .address(airportRequest.getAddress())
                .geoCode(airportRequest.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null) return null;

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
//                .timeZone(airport.getTimeZone())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static void updateEntity( AirportRequest request,Airport airport){
        if (airport == null || request == null) {
            return ;
        }

        if(request.getIataCode() != null) {
            airport.setIataCode(request.getIataCode().trim().toUpperCase());
        }
        if(request.getName() != null) {
            airport.setName(request.getName().trim().toUpperCase());
        }
        if (request.getAddress() !=null){
            airport.setAddress(request.getAddress());
        }
        if(request.getGeoCode() != null) {
            airport.setGeoCode(request.getGeoCode());
        }
    }
}
