package com.insurance.auth.controller;

import com.insurance.auth.dto.*;
import com.insurance.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =====================================================
    // LOGIN
    // =====================================================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // =====================================================
    // REGISTER
    // =====================================================
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

    // =====================================================
    // REFRESH TOKEN
    // =====================================================
    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {

        String newAccessToken =
                authService.refreshToken(request.refreshToken());

        // ✅ return new access token + same refresh token
        return new AuthResponse(
                newAccessToken,
                request.refreshToken()
        );
    }
}
