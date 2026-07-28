package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.domain.Category;
import com.likelion.moneylogbackend.domain.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransLogResponseDto(
        Long id,
        Type type,
        Integer amount,
        Category category,
        String description,
        LocalDate date,
        LocalDateTime createdAt
) {
    public static TransLogResponseDto from(TransLog t) {
        return new TransLogResponseDto(
                t.getId(),
                t.getType(),
                t.getAmount(),
                t.getCategory(),
                t.getDescription(),
                t.getDate(),
                t.getCreatedAt()
        );
    }
}
