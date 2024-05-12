package com.eshop.exception;

import org.springframework.security.core.AuthenticationException;

public class BaseAuthenticationException extends AuthenticationException {
    public BaseAuthenticationException(String msg) {
        super(msg);
    }
}
