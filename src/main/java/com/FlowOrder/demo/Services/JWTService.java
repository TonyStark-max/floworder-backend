package com.FlowOrder.demo.Services;

import com.FlowOrder.demo.Entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private String SECRET_KEY="2b5a111491ac7b95c0492fd0088b2c91aad0619cdeb3adb304115f223f6b65e5";
    private final Key key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(UserDetails userDetails){
        CustomUserDetails user=(CustomUserDetails) userDetails;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .claim("userId",user.getUserId())
                .claim("authorities",user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token,Function <Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
