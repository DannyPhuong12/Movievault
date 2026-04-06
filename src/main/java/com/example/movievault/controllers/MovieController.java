package com.example.movievault.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.movievault.repository.MovieRepository;

@Controller
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie-list";
    }
}