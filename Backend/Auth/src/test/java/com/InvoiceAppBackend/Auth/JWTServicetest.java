package com.InvoiceAppBackend.Auth;

import com.InvoiceAppBackend.Auth.service.*;

import io.jsonwebtoken.Claims;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class JwtServiceTest {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expiration-ms}")
    private long expirationMs;

    @Autowired
    private JWTService jwtService;

    String email = "test@example.com";

    //mock sample of Userdetails object to test jwtService.validateToken(email,userDetails);
    UserDetails userDetails = User.withUsername(email)
                .password("")
                .authorities("ROLE_USER") 
                .build();

    @Test
    void testGenerateToken()
    {
        String token = jwtService.generateToken(email);
        assertNotNull(token, "Token should not be null");
    }

    @Test
    void testClaims()
    {
        String token = jwtService.generateToken(email);
        assertNotNull(token, "Token should not be null");

        String emailExtracted = jwtService.extractUsername(token);
        assertEquals(email, emailExtracted, "Email does not match with email in token");

        Date expirationExtracted = jwtService.extractExpiration(token);
        assertNotNull(expirationExtracted);
        assertTrue(expirationExtracted.after(new Date()),"Error on expiration its date cannot be before now");

        Date issuedTime = jwtService.extractClaim(token, Claims::getIssuedAt);
        assertNotNull(issuedTime);
        assertTrue(issuedTime.before(new Date()), "Error on issuedAt its date cannot be after now");
    }

    @Test
    void testToken()
    {
        String token = jwtService.generateToken(email);
        assertNotNull(token, "Token should not be null");

        Boolean isTokenValid = jwtService.validateToken(token, userDetails);
        assertTrue(isTokenValid);
    }
}
        