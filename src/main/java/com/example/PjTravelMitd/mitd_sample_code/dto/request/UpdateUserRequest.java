package com.example.PjTravelMitd.mitd_sample_code.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String fullname;
    String address;
    String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;
    String citizenId;
    String avatar;
    Set<String> roles;
}
