package com.example.PjTravelMitd.mitd_sample_code.controller;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.ApiResponse;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.UserResponse;
import com.example.PjTravelMitd.mitd_sample_code.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    api tạo tài khoản khách hàng
    @PostMapping("/customers")
    public ApiResponse<UserResponse> addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.register(userDTO))
                .build();
    }
//    apo tạo tài khoản nhân viên
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public ApiResponse<UserResponse> createEmployee(@Valid @RequestBody UserRequestDTO userDTO) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createEmployee(userDTO))
                .build();
    }
}