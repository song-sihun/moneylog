package com.likelion.moneylogbackend.global.config;

import com.likelion.moneylogbackend.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 인증 없이 접근 허용할 경로 (화이트리스트)
    private static final String[] WHITELIST = {
            "/auth/**",
            "/h2-console/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(WHITELIST).permitAll() // 인증 없이 접근 허용
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
//                .formLogin(form -> form
//                        .loginPage("/auth/login") // 커스텀 로그인 페이지 지정
//                        .permitAll()
//                )
                .formLogin(form->form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout
                        .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    // 💡 CORS 세부 설정 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 프론트엔드 출처(Origin) 허용 (React 기본: 5173 / Vue 기본: 8080 / Next 기본: 3000 등)
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "http://127.0.0.1:5173"
        ));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 허용할 헤더 (Authorization 헤더 포함 필수!)
        configuration.setAllowedHeaders(List.of("*"));

        // 프론트엔드가 응답 헤더를 읽을 수 있도록 허용
        configuration.setExposedHeaders(List.of("Authorization"));

        // 쿠키/인증 정보 포함 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 API 경로에 적용
        return source;
    }
}