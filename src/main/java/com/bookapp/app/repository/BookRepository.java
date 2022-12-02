package com.bookapp.app.repository;

import com.bookapp.app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Book> findByLanguageName(String languageName);

    List<Book> findByAuthorName(String authorName);

    List<Book> findByPublisherName(String publisherName);

    List<Book> findByCategoryName(String categoryName);
}
