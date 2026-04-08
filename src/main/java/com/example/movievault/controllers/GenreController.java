package com.example.movievault.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.movievault.domain.Genre;
import com.example.movievault.repository.GenreRepository;

@Controller
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    public String getGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "genre-list";
    }

    @GetMapping("/genres/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genre-form";
    }

    @PostMapping("/genres/save")
    public String saveGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/genres";
    }

    @GetMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable("id") Long id, Model model) {
        model.addAttribute("genre", genreRepository.findById(id).orElseThrow());
        return "genre-form";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id) {
        genreRepository.deleteById(id);
        return "redirect:/genres";
    }
}