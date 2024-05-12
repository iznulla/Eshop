package com.eshop.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {
    @Value("${eshop.jwt.secret.key}")
    private String jwtSecret;

    @Value("${eshop.super-admin.login}")
    private String adminLogin;

    @Value("${eshop.super-admin.password}")
    private String adminPassword;
    @Value("{eshop.super-admin.phoneNumber}")
    private String phoneNumber;
}
