package com.InvoiceAppBackend.company.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig 
{
    @Value("${jwt.secret}")
    private String privateKey;

    @Value("#{'${app.cors.allowed-origins}'.split(',')}") //get url list from application.yaml
    private List<String> urlList;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            //this must be enforced by strong CORS rules. No XSRF-TOKEN. JWT go stateless
            .csrf(csrf -> csrf.disable()) 
            //JWT is statless no need to rely on sessions 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //Filter header and extract JWT payload to be used by controller, services ... 
            .addFilterBefore(new JwtAuthFilter(privateKey), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(urlList); // e.g. ["https://app.myfrontend.com", "http://localhost:4200"]
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Set-Cookie"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/company/**", config);
        source.registerCorsConfiguration("/api/clients/**", config);

        return source;
    }
}
