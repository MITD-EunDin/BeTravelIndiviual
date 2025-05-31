package com.example.PjTravelMitd.mitd_sample_code.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private int code = 1000;
    private String message = "Success";
    private T result;
}
