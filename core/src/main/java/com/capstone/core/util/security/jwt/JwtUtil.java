package com.capstone.core.util.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
@PropertySource("classpath:env.properties")
public class JwtUtil {
    private static String SECRET_KEY;
    private static String ISSUER;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    public static String createAccessToken(Long userId) {
        return JWT.create()
                .withSubject(Long.toString(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static String createRefreshToken(Long userId) {
        return JWT.create()
                .withSubject(Long.toString(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT validateToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    public static DecodedJWT decodeJWT(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    public static Long getUserIdFromToken(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = JwtUtil.decodeJWT(token);
        return Long.parseLong(decodedJWT.getSubject());
    }

    public static boolean isValidToken(String token) {
        try {
            JwtUtil.validateToken(token);
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
        return true;
    }

    @Value("${JWT_SECRET_KEY}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }
    public static String getSecretKey() {
        return SECRET_KEY;
    }
    @Value("${JWT_ISSUER}")
    public void setIssuer(String issuer) {
        ISSUER = issuer;
    }
    public static String getIssuer() {
        return ISSUER;
    }
}
