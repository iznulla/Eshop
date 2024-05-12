package com.eshop.service.auth;

import com.eshop.exception.LoginErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LogInResponseDto login(LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getLogin(),
                        requestDto.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            var jwtToken = jwtService.generateToken(requestDto.getLogin());
            return LogInResponseDto.builder().accessToken(jwtToken).build();
        }
        throw new LoginErrorException("Login failed");
    }
}
