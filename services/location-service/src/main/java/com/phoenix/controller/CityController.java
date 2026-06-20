package com.phoenix.controller;

import com.phoenix.payload.request.CityRequest;
import com.phoenix.payload.response.ApiResponse;
import com.phoenix.payload.response.CityResponse;
import com.phoenix.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity
            (@Valid @RequestBody CityRequest cityRequest) throws Exception {
        CityResponse res=cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById
            (@PathVariable Long id) throws Exception {
        CityResponse res=cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "10") int size,
            @RequestParam(name="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(name="sortDirection",defaultValue = "asc") String sortDirection
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity
            (@PathVariable Long id,
            @Valid @RequestBody CityRequest cityRequest) throws Exception {

        CityResponse res=cityService.updateCity(id, cityRequest);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable Long id)
            throws Exception {

        cityService.deleteCity(id);
        return ResponseEntity.ok(new ApiResponse("City deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.searchCities(keyword, pageable));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable("countryCode") String countryCode,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cityService.getAllCitiesByCountryCode(countryCode.toUpperCase(), pageable));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode){
        return ResponseEntity.ok(cityService.cityExists(cityCode.toUpperCase()));
    }
}
