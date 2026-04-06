package com.example.movievault.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    private int releaseYear;

    @ManyToOne
    private Genre genre;

    public Movie() {}

    public Movie(String title, String director, int releaseYear, Genre genre) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}