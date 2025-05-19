package com.example.reactbootserver.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity")
    private long refreshTokenValidity;

    private SecretKey key;

    public void initialize() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Assess토큰 생성
    public String createAccessToken(Long memberId, String email, String username, String nickname, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .claims()
                .subject(String.valueOf(memberId))
                .add("email", email)
                .add("username", username)
                .add("nickname", nickname)
                .add("role", role)
                .and()
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    //리프레쉬 토큰 생성
    public String createRefreshToken(Long memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

//    //공통 메서드
//    private String createToken(Long memberId, String email, String username, String nickname, String role, long validityTime) {
//        Date now = new Date();
//        Date expiry = new Date(now.getTime() + validityTime);
//
//        return Jwts.builder()
//                .claims()
//                .subject(String.valueOf(memberId))
//                .add("email", email)
//                .add("username", username)
//                .add("nickname", nickname)
//                .add("role", role)
//                .and()
//                .issuedAt(now)
//                .expiration(expiry)
//                .signWith(key)
//                .compact();
//    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getMemberId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String getRole(String token) {
        return (String) getClaims(token).get("role");
    }

    public boolean validateToken(@Nullable String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
