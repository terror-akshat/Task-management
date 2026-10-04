package practice.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import practice.example.demo.Entity.UserRole;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

@Component
public class JwtSecurity {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtSecurity(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(Long userId, String email, UserRole role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder().subject(email).claim("userId", userId).claim("role", role).issuedAt(now).expiration(expiry)
                .signWith(secretKey).compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {

        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}