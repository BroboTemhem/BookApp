package com.bookapp.app.repository;

import com.bookapp.app.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher,Integer> {
    Optional<Publisher> findPublisherByName(String name);
}
