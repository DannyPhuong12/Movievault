package com.example.movievault.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.movievault.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Iterable<Review> findByMovieId(Long movieId);
}