package com.shaon.jwtdemo.auth;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/jwt-demo/save";
    public static final String TOKEN_SECRET = "tcl123";
}
