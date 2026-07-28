package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.domain.Category;
import com.likelion.moneylogbackend.domain.Type;

import java.time.LocalDate;

//등록,수정 TransLog dto
public record TransLogRequestDto(
        Long id,
        Integer amount,
        Type type,
        Category category,
        String description,
        LocalDate date
) {
}