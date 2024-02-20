package com.jwtAuthLibrary.jwtBookAuthor.service;

import com.jwtAuthLibrary.jwtBookAuthor.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServices {

    @Value("${application.security.jwt.secrete-key}")
    private String secreteKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshToken;


    // Extract Claims
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private<T> T extractClaims(String token, Function<Claims, T> resolveClaims){
        final Claims claims = extractAllClaims(token);
        return resolveClaims.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] byteKey = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(byteKey);
    }

    //  generate Token
    public String generateToken(User user){
        return generateToken(new HashMap<>(),user);
    }

    public String generateRefreshToken(User user){
        return buildToken(new HashMap<>(), user, refreshToken);
    }

    private String generateToken(Map<String, Object> claims, User user){
        return buildToken(claims, user, jwtExpiration);
    }
    private String buildToken(Map<String, Object> claims,User user , long expiration){
        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setId(user.getUserId().toString())
                .claim("name",user.getName())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validation Token
    public boolean isValidToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return expiration(token).before(new Date());
    }

    private Date expiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
