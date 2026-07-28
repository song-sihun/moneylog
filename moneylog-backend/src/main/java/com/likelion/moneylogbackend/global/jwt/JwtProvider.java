package com.likelion.moneylogbackend.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    // 객체 생성 후 secretKey 문자열을 SecretKey 객체로 변환
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 1. JWT 토큰 생성 (로그인 성공 시 호출)
     */
    public String createToken(Long userId, String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)                  // sub: 로그인 계정명(email)
                .claim("userId", userId)         // 커스텀 클레임: DB의 user_id (PK)
                .issuedAt(now)                   // iat: 토큰 발급 시각
                .expiration(validity)            // exp: 토큰 만료 시각
                .signWith(key)                   // 서명 (SecretKey 적용)
                .compact();
    }

    /**
     * 2. 토큰에서 Email(Subject) 추출 (Filter에서 loadUserByUsername 호출용)
     */
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 3. 토큰에서 userId(PK) 추출 (필요 시 컨트롤러/서비스에서 사용)
     */
    public Long getUserIdFromToken(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    /**
     * 4. 토큰 유효성 검증 (Filter에서 요청 시마다 검증)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어있거나 잘못되었습니다.");
        }
        return false;
    }

    /**
     * 내부 공통 메서드: 토큰 복호화 및 Claims 파싱
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}