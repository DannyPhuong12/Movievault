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
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Review review = new Review();
        review.setMovie(movie);

        model.addAttribute("review", review);
        model.addAttribute("movie", movie);

        return "review-form";
    }

    @PostMapping("/reviews/save")
    public String saveReview(@Valid @ModelAttribute("review") Review review,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("movie", review.getMovie());
            return "review-form";
        }

        AppUser user = appUserRepository.findByUsername(principal.getName()).orElseThrow();
        review.setUser(user);

        Movie movie = movieRepository.findById(review.getMovie().getId()).orElseThrow();
        review.setMovie(movie);

        reviewRepository.save(review);
        return "redirect:/movies/" + review.getMovie().getId();
    }
}