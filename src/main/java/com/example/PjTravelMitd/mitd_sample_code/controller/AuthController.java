    package com.example.PjTravelMitd.mitd_sample_code.controller;

    import com.example.PjTravelMitd.mitd_sample_code.dto.request.AuthRequest;
    import com.example.PjTravelMitd.mitd_sample_code.dto.response.ApiResponse;
    import com.example.PjTravelMitd.mitd_sample_code.dto.response.AuthResponse;
    import com.example.PjTravelMitd.mitd_sample_code.dto.request.IntrospectRequest;
    import com.example.PjTravelMitd.mitd_sample_code.dto.response.IntrospectResponse;
    import com.example.PjTravelMitd.mitd_sample_code.service.AuthService;
    import com.nimbusds.jose.JOSEException;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.text.ParseException;

    @RestController
    @RequestMapping("/auth")
    @RequiredArgsConstructor
    public class AuthController {

        private final AuthService authenticationService;

        @PostMapping("/token")
        public ApiResponse<AuthResponse> authenticate(@RequestBody @Valid AuthRequest request) {
            return ApiResponse.<AuthResponse>builder()
                    .result(authenticationService.authenticate(request))
                    .build();
        }

    //    introspect lấy từ service
        @PostMapping("/introspect")
        ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
                throws ParseException, JOSEException {
            var result = authenticationService.introspect(request);
            return ApiResponse.<IntrospectResponse>builder()
                    .result(result)
                    .build();

        }
    }
