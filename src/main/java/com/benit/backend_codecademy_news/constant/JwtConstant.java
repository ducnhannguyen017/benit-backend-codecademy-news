package com.benit.backend_codecademy_news.constant;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtConstant {
    public static final long EXPIRATION_TIME_ACCESS_KEY = 20*60*1000;
    public static final long EXPIRATION_TIME_REFRESH_KEY = 30*24*60*60*1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static String SECRET_KEY;
    private final Environment environment;

    public JwtConstant(Environment environment) {
        this.environment = environment;
    }
    @PostConstruct
    public void init() {
        SECRET_KEY = environment.getProperty("JWT_KEY");
    }
}
