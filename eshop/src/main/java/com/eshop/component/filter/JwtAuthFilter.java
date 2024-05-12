package com.eshop.component.filter;


import com.eshop.exception.BaseAuthenticationException;
import com.eshop.exception.handler.ExceptionResponseDto;
import com.eshop.model.user.UserEntity;
import com.eshop.service.auth.JwtService;
import com.eshop.service.auth.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String login = null;
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
                login = jwtService.extractLogin(token);
            }
            if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userDetails = userService.loadUserByUsername(login);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new BaseAuthenticationException("User is not found or token is expired");
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(401);
            var responseBody = ExceptionResponseDto.builder()
                    .message("Token is expired")
                    .build();
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            MAPPER.writeValue(response.getOutputStream(), responseBody);
            return;
        }
    }
}
