package com.likelion.moneylogbackend.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;   //로그인 계정명
    private String password;    //로그인 비밀번호
    private String nickname; //활동명
    private LocalDateTime createdAt = LocalDateTime.now();
}
