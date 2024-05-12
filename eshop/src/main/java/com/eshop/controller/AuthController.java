package com.eshop.controller;

import com.eshop.service.auth.AuthService;
import com.eshop.service.auth.LogInResponseDto;
import com.eshop.service.auth.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}