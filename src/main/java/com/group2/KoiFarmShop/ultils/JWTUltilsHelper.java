package com.group2.KoiFarmShop.ultils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTUltilsHelper {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String data){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        String jws = Jwts.builder().subject(data).signWith(key).compact();
        return jws;
    }
}
