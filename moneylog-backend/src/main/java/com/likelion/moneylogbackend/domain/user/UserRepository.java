package com.likelion.moneylogbackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);   // 로그인 시 사용
    boolean existsByEmail(String email);         // 회원가입 중복 체크
}
