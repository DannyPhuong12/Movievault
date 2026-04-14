package com.example.movievault.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movievault.domain.AppUser;
import com.example.movievault.repository.AppUserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createDefaultUsers(AppUserRepository appUserRepository,
                                                PasswordEncoder passwordEncoder) {
        return args -> {
            if (appUserRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
                appUserRepository.save(admin);
            }
            if (appUserRepository.findByUsername("user").isEmpty()) {
                AppUser user = new AppUser("user", passwordEncoder.encode("user123"), "ROLE_USER");
                appUserRepository.save(user);
            }
        };
    }
}