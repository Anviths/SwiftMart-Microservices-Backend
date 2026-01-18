package com.swiftmaet.product_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;


    private SecretKey siningKey() {
        return Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(siningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public List<String> extractRoles(String token){
       Object roles= extractAllClaims(token).get("roles");
        return roles==null?List.of() : (List<String>) roles;
    }

    public int extractTokenVersion(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(siningKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.get("tv", Integer.class);


    }


     public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
     }
}
