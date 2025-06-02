package com.example.PjTravelMitd.mitd_sample_code.controller;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.ApiResponse;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.UserResponse;
import com.example.PjTravelMitd.mitd_sample_code.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/customers")
    public ApiResponse<UserResponse> addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Registration successful")
                .result(userService.register(userDTO))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public ApiResponse<UserResponse> createEmployee(@Valid @RequestBody UserRequestDTO userDTO) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Employee created successfully")
                .result(userService.createEmployee(userDTO))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ApiResponse<UserResponse> updateUserRole(@PathVariable Long id, @RequestBody Set<String> roles) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("User role updated successfully")
                .result(userService.updateUserRole(id, roles))
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping(params = "role")
    public ApiResponse<List<UserResponse>> getUsersByRole(@RequestParam String role) {
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Users retrieved successfully")
                .result(userService.getUsersByRole(role))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("User info retrieved successfully")
                .result(userService.getMyInfo())
                .build();
    }
}