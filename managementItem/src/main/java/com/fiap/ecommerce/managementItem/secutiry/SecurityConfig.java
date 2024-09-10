package com.fiap.ecommerce.managementItem.secutiry;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/item").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> {
                    exceptions
                            .authenticationEntryPoint((request, response, authException) -> {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
                            })
                            .accessDeniedHandler((request, response, accessDeniedException) -> {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
                            });
                });

        return http.build();
    }
}

