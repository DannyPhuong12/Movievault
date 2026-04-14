package com.example.movievault.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.movievault.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
}