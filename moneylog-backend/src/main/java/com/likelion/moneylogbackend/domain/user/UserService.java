package com.likelion.moneylogbackend.domain.user;

import com.likelion.moneylogbackend.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserSignUpResponseDto saveUser(UserSignUpRequestDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) throw new RuntimeException("이미 존재하는 계정");
        userRepository.save(User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .nickname(dto.nickname())
                        .createdAt(LocalDateTime.now())
                .build());

        return UserSignUpResponseDto.from(dto);
    }

    public UserLoginResponseDto login(UserLoginRequestDto request) {
        // (a) email로 User 조회 (없으면 예외 발생)
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // (b) passwordEncoder.matches(raw, encoded) 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // (c) 맞으면 jwtProvider.createToken(...) 호출
        String accessToken = jwtProvider.createToken(user.getId(), user.getEmail());

        // (d) 토큰 및 사용자 정보를 응답 DTO로 묶어서 반환
        return UserLoginResponseDto.of(accessToken, user);
    }

}
