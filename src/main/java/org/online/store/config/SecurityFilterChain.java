package org.online.store.config;

import org.online.store.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterChain {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

        @Bean
        public org.springframework.security.web.SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/product/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
                    .anyRequest().permitAll())
                    .csrf().disable()
                    .httpBasic(basic -> basic.disable())
                    .sessionManagement((session) -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

    }
