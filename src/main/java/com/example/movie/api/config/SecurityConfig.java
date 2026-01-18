package com.example.movie.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        // Public
                        .requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
                        // Admin
                        .requestMatchers(HttpMethod.POST, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")
                        // Login
                        .requestMatchers("/users/me/**").authenticated()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager users() {
        UserDetails user = User.withUsername("user")
                .password("{noop}123")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
