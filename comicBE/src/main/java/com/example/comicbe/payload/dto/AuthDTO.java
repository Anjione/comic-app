package com.example.comicbe.payload.dto;

import java.time.LocalDateTime;

public class AuthDTO {
    public record LoginRequest(String username, String password, String deviceId) {
    }

    public record Response(String message, String deviceId, String token, String refreshToken, UserAuthResponse user) {
    }

    public record RefreshTokenRequest(String refreshToken, String deviceId) {

    }

    public record RefreshTokenGenResponse(String token, LocalDateTime expireTime) {

    }

    public record LogoutRequest(String deviceId) {

    }
}