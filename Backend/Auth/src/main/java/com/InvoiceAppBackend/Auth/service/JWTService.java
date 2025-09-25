package com.InvoiceAppBackend.Auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService 
{
     
    //Load from env secret key + token expirency ->  field injection to String secret and expirationMS. 
    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expiration-ms}")
    private long expirationMS;

    /* 
    * Methods bellow are issuing JWT to Base64 String
    */

    //This build token object
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMS))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //This gennerates signature from secret key in app properties 
    private Key getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //This handles entry for dynamic field. like add tenantId, comapanyId etc..
    public String generateToken(String email) 
    { 
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }


    /* 
    * Methods vellow are for manipulate inner entries of JWT in String Base64
    */

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Claims claims = jwtService.extractClaim(token, null); ex-> claims.method(); You can acces all claims object class methods.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //used in public <T> T extractClaim()
    private Claims extractAllClaims(String token) 
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

     /* 
    * Methods bellow are for validateToken
    */
    private Boolean isTokenExpired(String token) 
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) 
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
