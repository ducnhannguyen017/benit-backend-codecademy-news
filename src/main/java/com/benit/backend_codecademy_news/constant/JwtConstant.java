package com.benit.backend_codecademy_news.constant;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtConstant {
    private final Environment environment;
    public static String secretKey;

    public JwtConstant(Environment environment) {
        this.environment = environment;
    }
    @PostConstruct
    public void init() {
        secretKey = environment.getProperty("JWT_KEY");
    }
}
