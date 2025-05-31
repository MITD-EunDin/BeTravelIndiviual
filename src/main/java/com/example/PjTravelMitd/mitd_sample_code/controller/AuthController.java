package com.example.PjTravelMitd.mitd_sample_code.controller;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.AuthRequest;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.ApiResponse;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.AuthResponse;
import com.example.PjTravelMitd.mitd_sample_code.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthResponse> authenticate(@RequestBody @Valid AuthRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }
}
