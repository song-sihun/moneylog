package com.likelion.moneylogbackend.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequestDto(
        @NotBlank String email,
        @NotBlank String password,
        String nickname) {
}