package com.example.PjTravelMitd.mitd_sample_code.dto.response;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.Builder;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String token;
    boolean authenticated;
}
