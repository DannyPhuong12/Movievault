package com.example.movievault.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.movievault.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}