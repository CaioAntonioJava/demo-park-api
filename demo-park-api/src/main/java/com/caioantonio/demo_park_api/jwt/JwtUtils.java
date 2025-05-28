package com.caioantonio.demo_park_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "47a92b4d578258b6f2a944e1f43e066dfc7e8a5cb24444412e97ff0b3dc7d4fe";

    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 30;


    private JwtUtils() {
    }

    // PREPARA A KEY P/ SER CRIPTOGRAFADA NO MOMENTO DE CRIAÇÃO DO TOKEN

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // CÁLCULO ENTRE DATA DE CRIAÇÃO E EXPIRAÇÃO DO TOKEN

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    // CRIAÇÃO DO TOKEN

    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();
        return new JwtToken(token);
    }

    // RECUPERAR CONTEÚDO DO TOKEN

    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException exception) {
            log.error(String.format("""
                    Tolen inválido %s
                    """, exception.getMessage()));
        }
        return null;
    }

    // RECUPERAR O USERNAME QUE ESTÁ NO TOKEN

    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // TESTA A VÁLIDADE DO TOKEN

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token));
            return true;
        } catch (JwtException exception) {
            log.error(String.format("""
                    Tolen inválido %s
                    """, exception.getMessage()));
        }
        return false;
    }

    // REFATORA O TOKEN P/ REMOVER A INSTRUÇÃO BEARER

    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}