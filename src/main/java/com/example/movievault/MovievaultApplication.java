package com.example.movievault;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.movievault.domain.Genre;
import com.example.movievault.domain.Movie;
import com.example.movievault.repository.GenreRepository;
import com.example.movievault.repository.MovieRepository;

@SpringBootApplication
public class MovievaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovievaultApplication.class, args);
		
	}

	@Bean
public CommandLineRunner demo(GenreRepository genreRepo, MovieRepository movieRepo) {
    return (args) -> {

        Genre action = new Genre("Action");
        Genre drama = new Genre("Drama");

        genreRepo.save(action);
        genreRepo.save(drama);

        movieRepo.save(new Movie("Inception", "Christopher Nolan", 2010, action));
        movieRepo.save(new Movie("Interstellar", "Christopher Nolan", 2014, drama));
    };
}

}
