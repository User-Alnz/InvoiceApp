package com.InvoiceAppBackend.Auth;

import com.InvoiceAppBackend.Auth.service.*;
import com.InvoiceAppBackend.Auth.model.*;

import io.jsonwebtoken.Claims;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.jsonwebtoken.security.SignatureException;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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
    private UserInfoDetails buildMockUserDetails() 
    {
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());

        UserInfo mockUser = new UserInfo();
        mockUser.setEmail(email);
        mockUser.setPassword("password"); 
        mockUser.setRole(Role.ADMIN);
        mockUser.setTenant(tenant);
        
        return new UserInfoDetails(mockUser);
    }

    private String modifyPayLoadInJWT(String decodedPayLoad, String patternToReplace, String replaceBy)
    {
        String regex = Pattern.quote(patternToReplace);
        
        Pattern patternToFind = Pattern.compile(regex);
        Matcher searchPatternInString = patternToFind.matcher(decodedPayLoad);
        
        String newPayload = searchPatternInString.replaceFirst(replaceBy);
        
        return newPayload;
    }

    @Test
    void testGenerateToken()
    {
        UserInfoDetails userDetails = buildMockUserDetails();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token, "Token should not be null");
    }

    @Test
    void testValidateToken() 
    {
        UserInfoDetails userDetails = buildMockUserDetails();

        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testClaims()
    {
        UserInfoDetails userDetails = buildMockUserDetails();

        String token = jwtService.generateToken(userDetails);
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
        UserInfoDetails userDetails = buildMockUserDetails();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token, "Token should not be null");

        Boolean isTokenValid = jwtService.validateToken(token, userDetails);
        assertTrue(isTokenValid);


        //Test invalid Token for a different email in token.

        //Create Mock Token for test------------------------------
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());
        
        UserInfo wrongInfo = new UserInfo();
        wrongInfo.setEmail("patoche@gmail.com");
        wrongInfo.setPassword("password");
        wrongInfo.setRole(Role.USER);
        wrongInfo.setTenant(tenant);

        UserInfoDetails wrongDetails = new UserInfoDetails(wrongInfo);
        String corruptedToken = jwtService.generateToken(wrongDetails);
        //---------------------------------------------------------

        //modify email -------------------------------------------
        wrongInfo.setEmail("patrick@gmail.com");
        UserInfoDetails fakeRightDetails = new UserInfoDetails(wrongInfo);
        //---------------------------------------------------------
        

        Boolean isWrongUser = jwtService.validateToken(corruptedToken, fakeRightDetails);
        assertFalse(isWrongUser, "Token should be invalid for a different user");
    }

    @Test
    void jWTForgeryAttack()
    {
        //Create Mock Token for test------------------------------
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());

        UserInfo user = new UserInfo();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRole(Role.USER); //role will be changed for admin
        user.setTenant(tenant);

        UserInfoDetails userDetails = new UserInfoDetails(user);
        String token = jwtService.generateToken(userDetails);
        //---------------------------------------------------------


        //Corrupt Token by changing role in Token For ADMIN --------------------------------------------------
        String[] arrayOntoken = token.split("\\.");// arrayOntoken : [0]= header, [1]=payLaod, [2]=signature
        byte[] decoded = Base64.getDecoder().decode(arrayOntoken[1]);
        String decodedPayLoad = new String(decoded, StandardCharsets.UTF_8);
        //decodedPayLoad exemple -> {"roles":["USER"],"tenantId":"889180e9-5801-47cb-ac6a-91178d7c5add","sub":"test@gmail.com","iat":1759071566,"exp":1759073366}

        String newToken = modifyPayLoadInJWT(decodedPayLoad,"\"roles\":[\"USER\"]", "\"roles\":[\"ADMIN\"]");
        
        String reEncoded = Base64.getUrlEncoder()
                              .withoutPadding()
                              .encodeToString(newToken.getBytes(StandardCharsets.UTF_8));
        //----------------------------------------------------------------------------------------------------------

                              
       String FalsifiedToken = arrayOntoken[0] + "." + reEncoded + "." + arrayOntoken[2];
        
        assertThrows(SignatureException.class, () -> 
        {
            jwtService.validateToken(FalsifiedToken, userDetails);
        }, "Forged JWT should fail signature verification");

    }
}
        