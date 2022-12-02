package com.bookapp.app.repository;

import com.bookapp.app.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author,UUID> {
    Optional<Author> findAuthorByName(String name);
}
