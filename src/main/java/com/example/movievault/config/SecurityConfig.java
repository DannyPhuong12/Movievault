package com.example.movievault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    .authorizeHttpRequests(auth -> auth
    .requestMatchers("/", "/movies", "/genres", "/css/**").permitAll()
    .requestMatchers("/movies/*").permitAll()
    .requestMatchers("/h2-console/**").permitAll()

    .requestMatchers("/movies/add", "/movies/save").hasAnyRole("USER", "ADMIN")
    .requestMatchers("/movies/edit/**").hasAnyRole("USER", "ADMIN")
    .requestMatchers("/movies/delete/**").hasRole("ADMIN")

    .requestMatchers("/movies/*/reviews/add", "/reviews/save").hasAnyRole("USER", "ADMIN")

    .requestMatchers("/genres/add", "/genres/save", "/genres/edit/**", "/genres/delete/**").hasRole("ADMIN")

    .anyRequest().authenticated()
)

            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable()))
            .formLogin(form -> form
                .defaultSuccessUrl("/movies", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/movies")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}