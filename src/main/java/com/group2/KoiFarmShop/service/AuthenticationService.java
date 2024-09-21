package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.IntrospectResponse;
import com.group2.KoiFarmShop.dto.request.IntrospectRequest;
import com.group2.KoiFarmShop.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    AccountRepository accountRepository;
    @Value("${jwt.secret}")
    private String jwtSecret;
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        IntrospectResponse response = new IntrospectResponse();

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            response.setValid(accountRepository.existsByEmail(claims.getSubject()));
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            response.setValid(false);
            // Xử lý khi token đã hết hạn
        } catch (io.jsonwebtoken.SignatureException e) {
            response.setValid(false);
            // Xử lý khi chữ ký không hợp lệ
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            response.setValid(false);
            // Xử lý khi token không đúng định dạng
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            response.setValid(false);
            // Xử lý khi loại mã hóa không được hỗ trợ
        } catch (Exception e) {
            response.setValid(false);
            // Xử lý cho các trường hợp ngoại lệ khác
        }

        return response;
    }
}
