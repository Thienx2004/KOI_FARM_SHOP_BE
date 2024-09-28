package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.IntrospectResponse;
import com.group2.KoiFarmShop.dto.request.IntrospectRequest;
import com.group2.KoiFarmShop.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class AuthenticationService {
    AccountRepository accountRepository;
    @Value("${jwt.secret}")
    private String jwtSecret;




    public String validateTokenByEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
