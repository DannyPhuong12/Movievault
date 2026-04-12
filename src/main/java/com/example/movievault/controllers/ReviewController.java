package com.example.movievault.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.movievault.domain.AppUser;
import com.example.movievault.domain.Movie;
import com.example.movievault.domain.Review;
import com.example.movievault.repository.AppUserRepository;
import com.example.movievault.repository.MovieRepository;
import com.example.movievault.repository.ReviewRepository;

import jakarta.validation.Valid;

@Controller
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final AppUserRepository appUserRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            MovieRepository movieRepository,
                            AppUserRepository appUserRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/movies/{movieId}/reviews/add")
    public String showAddReviewForm(@PathVariable("movieId") Long movieId, Model model) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        Review review = new Review();

        model.addAttribute("review", review);
        model.addAttribute("movie", movie);

        return "review-form";
    }

    @PostMapping("/movies/{movieId}/reviews/add")
    public String saveReview(@PathVariable("movieId") Long movieId,
                             @Valid @ModelAttribute("review") Review review,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("movie", movie);
            return "review-form";
        }

        if (principal == null) {
            throw new RuntimeException("User is not logged in.");
        }

        AppUser user = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));

        review.setMovie(movie);
        review.setUser(user);

        reviewRepository.save(review);

        return "redirect:/movies/" + movieId;
    }
}