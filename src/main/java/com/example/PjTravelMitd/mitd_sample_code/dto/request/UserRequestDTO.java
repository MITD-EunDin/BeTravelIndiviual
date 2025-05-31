package com.example.PjTravelMitd.mitd_sample_code.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "Use must not blank")
    @Size(min = 3, message = "User must more 3 word")
    String username;
    @NotBlank(message = "Password must not null")
    String password;
    @Email(message = "Emaail invalid format")
    @NotBlank(message = "Email must not blank")
    String email;
    String fullname;
    String address;
    Set<String> role;
    @Pattern(regexp = "^\\d{10}$", message = "Phone invalid format")
    String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;
    String citizenId;
    String avatar;
}
