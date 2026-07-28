package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.domain.Category;
import com.likelion.moneylogbackend.domain.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TransLogDateRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,  // 조회 시작일

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate   // 조회 종료일
) {
}
