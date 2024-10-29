package com.senai.engSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String URL = "/api";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager
                .userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(URL + "/").permitAll() /* Uma rota pública, acessível a todos os usuários */
                        .requestMatchers(URL + "/user").hasRole("USER") /* Uma rota restrita para usuários autenticados com o papel de "USER" */
                        .requestMatchers(URL + "/admin").hasRole("ADMIN") /* Uma rota exclusiva para administradores com o papel de "ADMIN" */
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
