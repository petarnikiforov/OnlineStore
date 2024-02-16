package org.online.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityFilterChain {

        @Bean
        public org.springframework.security.web.SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests( auth -> {
                        auth
                                .anyRequest().permitAll();
                    })
                    .csrf().disable()
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement((session) -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            return http.build();
        }

    }
