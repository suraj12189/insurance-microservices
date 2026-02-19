package com.insurance.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}


