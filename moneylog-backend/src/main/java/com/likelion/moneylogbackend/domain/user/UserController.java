package com.likelion.moneylogbackend.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signUpUser(@RequestBody @Valid UserSignUpRequestDto dto){
        return ResponseEntity.ok(userService.saveUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(
            @Valid @RequestBody UserLoginRequestDto req
    ) {
        UserLoginResponseDto response = userService.login(req);
        return ResponseEntity.ok(response);
    }
}
