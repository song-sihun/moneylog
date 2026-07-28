package com.likelion.moneylogbackend.domain.user;

public record UserLoginResponseDto(
        String accessToken,
        String tokenType,   // 보통 "Bearer"
        String email,
        String nickname
) {
    public static UserLoginResponseDto of(String accessToken, User user) {
        return new UserLoginResponseDto(
                accessToken,
                "Bearer",
                user.getEmail(),
                user.getNickname()
        );
    }
}