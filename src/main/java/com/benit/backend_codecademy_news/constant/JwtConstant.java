package com.benit.backend_codecademy_news.constant;

public class JwtConstant {
    public static final String SECRET_KEY="secretkey";
    public static final long EXPIRATION_TIME_ACCESS_KEY = 20*60*1000;
    public static final long EXPIRATION_TIME_REFRESH_KEY = 30*24*60*60*1000;
    public static final String TOKEN_PREFIX = "Bearer ";
}
