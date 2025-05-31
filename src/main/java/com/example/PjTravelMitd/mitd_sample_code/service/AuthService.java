package com.example.PjTravelMitd.mitd_sample_code.service;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.AuthRequest;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.AuthResponse;
import com.example.PjTravelMitd.mitd_sample_code.exception.AppException;
import com.example.PjTravelMitd.mitd_sample_code.exception.ErrorCode;
import com.example.PjTravelMitd.mitd_sample_code.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return new AuthResponse(token, true);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
