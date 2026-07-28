package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.domain.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TransLogRepository extends JpaRepository<TransLog,Long> {
    //기간 내 내역 조회
    Page<TransLog> findByUserAndDateBetween(User user, LocalDate start, LocalDate end, Pageable pageable);

    //User 제외 테스트 용
    Page<TransLog> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    Optional<TransLog> findByIdAndUser_Id(Long id, Long user_Id);
}
