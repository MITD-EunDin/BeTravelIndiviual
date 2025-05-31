package com.example.PjTravelMitd.mitd_sample_code.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {
    @NotBlank(message = "username must not blank")
    String username;
    @NotBlank(message = "password must not blank")
    String password;
}
