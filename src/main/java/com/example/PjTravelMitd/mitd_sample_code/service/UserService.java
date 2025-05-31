package com.example.PjTravelMitd.mitd_sample_code.service;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UpdateUserRequest;
import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.UserResponse;
import com.example.PjTravelMitd.mitd_sample_code.entity.User;
import com.example.PjTravelMitd.mitd_sample_code.exception.AppException;
import com.example.PjTravelMitd.mitd_sample_code.exception.ErrorCode;
import com.example.PjTravelMitd.mitd_sample_code.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toSet())
        );
    }

    public UserResponse register(UserRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTS);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setCitizenId(request.getCitizenId());
        user.setAvatar(request.getAvatar());
        user.setRoles(request.getRoles() != null ? request.getRoles() : Set.of("CUSTOMER"));

        user = userRepository.save(user);
        return mapToUserResponse(user);
    }

    public UserResponse createEmployee(UserRequestDTO request) {
        if (!request.getRoles().contains("STAFF")) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
        return register(request);
    }

    public List<UserResponse> getUser() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByRole(String role) {
        return userRepository.findByRolesContaining(role).stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setFullname(request.getFullname());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setCitizenId(request.getCitizenId());
        user.setAvatar(request.getAvatar());
        if (request.getRoles() != null) {
            user.setRoles(request.getRoles());
        }

        user = userRepository.save(user);
        return mapToUserResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullname(user.getFullname());
        response.setAddress(user.getAddress());
        response.setPhone(user.getPhone());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setCitizenId(user.getCitizenId());
        response.setAvatar(user.getAvatar());
        response.setRoles(user.getRoles());
        return response;
    }
}
