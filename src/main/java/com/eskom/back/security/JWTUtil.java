package com.eskom.back.security;

import com.eskom.back.tenantConfig.TenantContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {
    @Value("${app.secret.key}")
    private String secret_key;

    // code to generate Token
    public String generateToken(String subject) {
        String tokenId = String.valueOf(new Random().nextInt(10000));
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenant", TenantContext.getCurrentTenant());
        return Jwts.builder()
                .setId(tokenId)
                .setSubject(subject)
                .addClaims(claims)
                .setIssuer("ESKOM")
                .setAudience("ESKOM")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(365)))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret_key.getBytes()))
                .compact();
    }

    // code to get Claims
    public Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(secret_key.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    // code to check if token is valid
    public boolean isValidToken(String token) {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    // code to check if token is valid as per username
    public boolean isValidToken(String token, String username,String tenant) {
        String tokenUserName = getSubject(token);
        return (username.equals(tokenUserName) && !isTokenExpired(token) && isCrossTenant(token,tenant));
    }

    // code to check if token is expired
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    //code to get expiration date
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public Boolean isCrossTenant (String token,String header) {
        return getClaims(token).get("tenant").equals(header) ;
    }

    //code to get expiration date
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }


}
