package com.example.PjTravelMitd.mitd_sample_code.service;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.UserResponse;
import com.example.PjTravelMitd.mitd_sample_code.entity.User;
import com.example.PjTravelMitd.mitd_sample_code.exception.AppException;
import com.example.PjTravelMitd.mitd_sample_code.exception.ErrorCode;
import com.example.PjTravelMitd.mitd_sample_code.mapper.UserMapper;
import com.example.PjTravelMitd.mitd_sample_code.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse register(UserRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUserRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("CUSTOMER")); // Gán vai trò CUSTOMER mặc định
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

//    tạo tài khoản nhân viên
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createEmployee(UserRequestDTO request) {
        if (!request.getRole().contains("STAFF")) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUserRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}