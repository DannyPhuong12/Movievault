package com.example.movievault.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.movievault.domain.Movie;
import com.example.movievault.repository.GenreRepository;
import com.example.movievault.repository.MovieRepository;
import com.example.movievault.repository.ReviewRepository;

import jakarta.validation.Valid;

@Controller
public class MovieController {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;
    
    public MovieController(MovieRepository movieRepository,
                           GenreRepository genreRepository,
                           ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/movies";
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie-list";
    }

  
    @GetMapping("/movies/add")
    public String showAddMovieForm(Model model) {
    model.addAttribute("movie", new Movie());
    model.addAttribute("genres", genreRepository.findAll());
    return "movie-form";
}

    @GetMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        model.addAttribute("genres", genreRepository.findAll());
        return "movie-form";
    }

    @PostMapping("/movies/save")
    public String saveMovie(@Valid @ModelAttribute("movie") Movie movie,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreRepository.findAll());
            return "movie-form";
        }

        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id) {
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/movies/{id}")
    public String showMovieDetails(@PathVariable("id") Long id, Model model) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviewRepository.findByMovieId(id));
        return "movie-details";
    }

    @GetMapping("/genres/{id}/movies")
    public String getMoviesByGenre(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movies", movieRepository.findByGenre_Id(id));
        model.addAttribute("selectedGenreId", id);
        return "movie-list";
    }
}