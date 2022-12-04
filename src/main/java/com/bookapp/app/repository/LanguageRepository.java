package com.bookapp.app.repository;

import com.bookapp.app.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByName(String name);
    boolean existsByNameContainingIgnoreCase(String name);
}
