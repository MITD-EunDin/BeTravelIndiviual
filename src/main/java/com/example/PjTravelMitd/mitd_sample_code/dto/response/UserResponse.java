package com.example.PjTravelMitd.mitd_sample_code.dto.response;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String email;
    String fullname;
    String address;
    String phone;
    LocalDate dateOfBirth;
    String citizenId;
    String avatar;
    Set<String> roles;
}
