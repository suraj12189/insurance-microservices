package com.insurance.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthModeSelector {

    @Value("${auth.mode:jwt}")
    private String authMode;

    public boolean isJwtMode(){
        return "jwt".equalsIgnoreCase(authMode);
    }

    public boolean isOauthMode(){
        return "oauth".equalsIgnoreCase(authMode);
    }
}
