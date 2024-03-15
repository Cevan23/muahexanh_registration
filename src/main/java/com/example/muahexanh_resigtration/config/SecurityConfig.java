package com.example.muahexanh_resigtration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String MY_SECRET_KEY = "muahexanh";
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        SecureRandom secureRandom = new SecureRandom(MY_SECRET_KEY.getBytes());
        return new BCryptPasswordEncoder(10, secureRandom);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .anyRequest().permitAll();
        return http.build();
    }
}