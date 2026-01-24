package com.FlowOrder.demo.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecConfig {
    private final JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/user/register","/user/login","/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .requestMatchers("/user/testing","/api/orders/createOrder","/api/payments/payment/{orderId}","/api/logistics/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}
