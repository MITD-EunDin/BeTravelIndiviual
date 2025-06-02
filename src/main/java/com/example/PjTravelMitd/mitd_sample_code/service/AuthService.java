package com.example.PjTravelMitd.mitd_sample_code.service;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.AuthRequest;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.AuthResponse;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.IntrospectResponse;
import com.example.PjTravelMitd.mitd_sample_code.dto.request.IntrospectRequest;
import com.example.PjTravelMitd.mitd_sample_code.entity.User;
import com.example.PjTravelMitd.mitd_sample_code.repository.UserRepository;
import com.example.PjTravelMitd.mitd_sample_code.exception.AppException;
import com.example.PjTravelMitd.mitd_sample_code.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;

    @NonFinal
    @Value("${spring.security.jwt.signer-key}")
    String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid(verified && expiryTime.after(new Date()))
                    .build();
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return IntrospectResponse.builder().valid(false).build();
        }
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            log.info("Authenticating user: {}", request.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            String token = generateToken(user);
            log.info("Authentication successful for user: {}", request.getUsername());
            return AuthResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for user: {}", request.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}. Error: {}", request.getUsername(), e.getMessage());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

//    public AuthResponse authenticate(AuthRequest request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//            User user = userRepository.findByUsername(request.getUsername())
//                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//            String token = generateToken(user);
//            return AuthResponse.builder()
//                    .token(token)
//                    .authenticated(true)
//                    .build();
//        } catch (Exception e) {
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
//    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String scope = buildScope(user);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("tondien")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", scope)
                .claim("user_id", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }

    public String createTokenForUser(User user) {
        return generateToken(user);
    }
}