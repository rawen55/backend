package Telemedcine.cwa.telemedcine.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import Telemedcine.cwa.telemedcine.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private static final String SECRET_KEY = "my-very-secret-key-which-should-be-long-enough";

    private Key getSigningKey() {
        // Ensure the key is properly encoded and long enough
        byte[] keyBytes = Base64.getEncoder().encode(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", List.of(  user.getRole().name()));
        extraClaims.put("userId", user.getId());
    
        return createToken(extraClaims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> {
            Object userId = claims.get("userId");
            if (userId instanceof Integer integer) {
                return integer.longValue();
            } else if (userId instanceof Long) {
                return (Long) userId;
            }
            throw new IllegalArgumentException("Invalid userId type");
        });
    }

    public String extractUserRole(String token) {
        return extractClaim(token, claims -> {
            Object role = claims.get("role");
            return role != null ? role.toString() : null;
        });
    }
}