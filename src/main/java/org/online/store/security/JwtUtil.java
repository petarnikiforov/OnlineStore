package org.online.store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.online.store.models.Userr;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    public final String SECRET = "veryultragigamaxisupersecretkeyspammingsomeletterstoreachtherequiredsize";
    private final long EXPIRATION = 1000*60*60*24;
    public String generateToken(Userr user) {
        return Jwts.builder()
                .setSubject(user.getUsername().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .claim("role", user.getRole().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Object role = extractAllClaims(token).get("role");
        return role == null ? null : role.toString(); // "ADMIN"
    }

    public boolean isTokenValid(String token) {
        try {
            Claims c = extractAllClaims(token);
            return c.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
