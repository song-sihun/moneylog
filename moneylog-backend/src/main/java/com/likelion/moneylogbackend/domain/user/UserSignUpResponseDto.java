package com.likelion.moneylogbackend.domain.user;

public record UserSignUpResponseDto(
        String email,
        String nickname
) {
    public static UserSignUpResponseDto from(UserSignUpRequestDto dto){
        return new UserSignUpResponseDto(dto.email(),dto.nickname());
    }
}
