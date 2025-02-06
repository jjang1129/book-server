package com.server.book.config;

import com.server.book.domain.User;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class TokenProvider {

    //private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public  String create(User user){

        return Jwts.builder()
                .signWith(secretKey)
                .setClaims(Map.of(  // 토큰에 넣으려고하는거
                        "email", user.getEmail()


                ))
                .setIssuedAt(new Date()) // 토큰 발급 날짜
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS))) // 토큰 유효기간
                .compact();
    }

    public User validate(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return User.builder()
                .email((String) claims.get("email"))
                .build();
    }
}
