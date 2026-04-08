package com.example.movievault.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.movievault.domain.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}