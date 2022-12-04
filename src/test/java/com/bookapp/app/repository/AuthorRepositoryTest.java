package com.bookapp.app.repository;

import com.bookapp.app.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void whenFindAuthorByNameCalledValidRequest_thenReturnAuthor() {
        //given
        Author author = new Author();
//        author.setId(UUID.fromString("f159774c-5214-4e36-aa75-8ebf3b8d5427"));
        author.setName("test");
        author.setBooks(List.of());

        authorRepository.save(author);
        //when
        Optional<Author> expected = authorRepository.findByName("test");
        //then
        assertNotNull(expected.get().getId());
        assertEquals(author.getName(),expected.get().getName());
        assertEquals(author.getBooks(),expected.get().getBooks());
    }




















}