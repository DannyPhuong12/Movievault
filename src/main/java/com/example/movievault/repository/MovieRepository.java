package com.example.movievault.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.movievault.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Iterable<Movie> findByGenre_Id(Long genreId);
}