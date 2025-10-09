package com.InvoiceAppBackend.company.config;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.io.IOException;
import java.security.Key;

public class JwtAuthFilter extends OncePerRequestFilter
{
    private final String secretKey;

    JwtAuthFilter(String secretKey)
    {
        this.secretKey = secretKey;
    }

    private Key getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractJWT(Cookie[] cookies)
    {
        if(cookies != null) 
        {
            for(Cookie cookie : cookies) 
            {
                if("jwt".equals(cookie.getName())) 
                return cookie.getValue();
            }
        }
        return null;
    }

    private Claims validateToken(String token)
    {
    
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // verify signature from secretKey
                .build()
                .parseClaimsJws(token) // verify matching claims and signature
                .getBody();
    }


    /*
     *  Specially design for Http only + JWT from cookies. 
     */
    @Override
    protected void doFilterInternal(
        @NonNull    HttpServletRequest request,
        @NonNull    HttpServletResponse response,
        @NonNull    FilterChain filterChain ) throws ServletException, IOException
    {

        Cookie[] cookies = request.getCookies();
        String  token = null;

        token = extractJWT(cookies);

        if(token == null)
        {
            // Response - 401 Unauthorized 
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        try
        {
            // Validate JWT 
            Claims claims = validateToken(token);
            String username = claims.getSubject();

            // Build authentication token 
            UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Pass next 
            filterChain.doFilter(request, response);
        } 
        catch (JwtException error) // catch error from validateToken()
        {   
            // Response - 401 Unauthorized 
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
    }
}
