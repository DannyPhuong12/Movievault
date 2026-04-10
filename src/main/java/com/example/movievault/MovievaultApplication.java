package com.example.movievault;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movievault.domain.AppUser;
import com.example.movievault.domain.Genre;
import com.example.movievault.domain.Movie;
import com.example.movievault.repository.AppUserRepository;
import com.example.movievault.repository.GenreRepository;
import com.example.movievault.repository.MovieRepository;

@SpringBootApplication
public class MovievaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovievaultApplication.class, args);
    }

   @Bean
public CommandLineRunner demo(GenreRepository genreRepository,
                              MovieRepository movieRepository,
                              AppUserRepository appUserRepository,
                              PasswordEncoder passwordEncoder) {
    return (args) -> {

       
        if (appUserRepository.findByUsername("user").isEmpty()) {

            AppUser user = new AppUser("user", passwordEncoder.encode("user123"), "ROLE_USER");
            AppUser admin = new AppUser("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");

            appUserRepository.save(user);
            appUserRepository.save(admin);
        }

        
        if (genreRepository.count() == 0) {

            Genre action = new Genre("Action");
            Genre drama = new Genre("Drama");

            genreRepository.save(action);
            genreRepository.save(drama);

            movieRepository.save(new Movie("Inception", "Christopher Nolan", 2010, action));
            movieRepository.save(new Movie("Interstellar", "Christopher Nolan", 2014, drama));
        }
    };
}
}