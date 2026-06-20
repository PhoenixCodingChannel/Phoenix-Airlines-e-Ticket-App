package com.phoenix.controller;

import com.phoenix.payload.request.AirportRequest;
import com.phoenix.payload.response.AirportResponse;
import com.phoenix.payload.response.ApiResponse;
import com.phoenix.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest airportRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airportService.createAirport(airportRequest)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById
            (@PathVariable Long id) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(
                airportService.getAirportById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports(){
        return ResponseEntity.status(HttpStatus.OK).body(
                airportService.getAllAirports()
        );
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportsByCityId
            (@PathVariable Long cityId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(
                airportService.getAirportByCityId(cityId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport
            (@PathVariable Long id,
            @Valid @RequestBody AirportRequest airportRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(
                airportService.updateAirport(id, airportRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(@PathVariable Long id) throws Exception {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(new ApiResponse("Airport deleted successfully"));
    }
}
