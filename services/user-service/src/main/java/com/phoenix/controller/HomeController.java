package com.phoenix.controller;

import com.phoenix.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        ApiResponse apiResponse=new ApiResponse("Hello Everyone! I am the passenger service of Phoenix Airlines");
        return apiResponse;
    }
}
