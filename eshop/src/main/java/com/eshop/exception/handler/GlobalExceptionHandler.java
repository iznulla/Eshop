package com.eshop.exception.handler;

import com.eshop.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class, BadDataException.class, LoginErrorException.class})
    public ResponseEntity<ExceptionResponseDto> catchResourceNotFoundException(RuntimeException e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = {DataDuplicationException.class})
    public ResponseEntity<ExceptionResponseDto> catchDataDuplicationException(DataDuplicationException e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponseDto> handleDMSRESTException(MethodArgumentNotValidException e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {BaseAuthenticationException.class})
    public ResponseEntity<ExceptionResponseDto> handleBaseAuthenticationException(BaseAuthenticationException e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ExceptionResponseDto> handleExpiredJwtException(ExpiredJwtException e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionResponseDto> handleExpiredJwtException(Exception e) {
        var id = UUID.randomUUID();
        log.error("ID " + id, e);
        var response = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .errorId(id)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
