package com.Lostfound.lostfound.Controller.Utils;
import com.Lostfound.lostfound.Model.Item;
import com.Lostfound.lostfound.Model.user;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MySuperSecretKey";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    public String generateToken(user user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", user.getId());

// Print claims to console
        System.out.println("JWT Claims:");
        claims.forEach((k, v) -> System.out.println(k + " : " + v));

// Generate token
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }

    // Validate token
    public boolean validateToken(String token, String email) {
        String tokenEmail = getuserid(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    // Get email from token
    public static String getuserid(String token) {
        System.out.println("✅ Item ID from JWT : " + token);

        return getAllClaimsFromToken(token).get("userid", String.class);

    }

    // Get all claims from token
    public static Claims getAllClaimsFromToken(String token) {
        System.out.println("✅ Item ID from JWT (here): " + token);

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}