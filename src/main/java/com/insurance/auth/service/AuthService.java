package com.insurance.auth.service;

import com.insurance.auth.dto.LoginRequest;
import com.insurance.auth.dto.RegisterRequest;
import com.insurance.auth.dto.AuthResponse;
import com.insurance.auth.entity.User;
import com.insurance.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authManager,
                       JwtEncoder jwtEncoder,
                       JwtDecoder jwtDecoder,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {

        this.authManager = authManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // =====================================================
    // LOGIN (returns BOTH tokens)
    // =====================================================
    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()));

        String accessToken = generateAccessToken(authentication);
        String refreshToken = generateRefreshToken(authentication);

        return new AuthResponse(accessToken, refreshToken);
    }

    // =====================================================
    // REGISTER USER
    // =====================================================
    public void register(RegisterRequest request) {

        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        userRepository.save(user);
    }

    // =====================================================
    // REFRESH TOKEN
    // =====================================================
    public String refreshToken(String refreshToken) {

        Jwt jwt = jwtDecoder.decode(refreshToken);

        // ✅ Ensure this is refresh token
        if (!"refresh".equals(jwt.getClaim("type"))) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwt.getSubject();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of() // roles not required here
                );

        return generateAccessToken(authentication);
    }

    // =====================================================
    // ACCESS TOKEN
    // =====================================================
    private String generateAccessToken(Authentication authentication) {

        Instant now = Instant.now();

        var roles = authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer("insurance-auth-service")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600)) // 1 hour
                .claim("roles", roles)
                .claim("type", "access")
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    // =====================================================
    // REFRESH TOKEN
    // =====================================================
    private String generateRefreshToken(Authentication authentication) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer("insurance-auth-service")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(86400)) // 24 hours
                .claim("type", "refresh")
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
