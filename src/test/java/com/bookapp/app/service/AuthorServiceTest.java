package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateAuthorRequest;
import com.bookapp.app.dto.requests.UpdateAuthorRequest;
import com.bookapp.app.dto.responses.GetAllAuthorResponse;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Author;
import com.bookapp.app.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private AuthorService authorService;
    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorService = new AuthorService(authorRepository);
    }

    @Test
    @DisplayName("When Get All Author Called")
    void whenGetAllAuthorCalled() {
        GetAllAuthorResponse authorBuild = GetAllAuthorResponse.builder()
                .id(UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256"))
                .name("testName")
                .build();

        Author author = new Author();
        author.setId(UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256"));
        author.setName("testName");

        authorService.getAllAuthor();
        verify(authorRepository).findAll();

        assertEquals(authorBuild.getId().toString(),author.getId().toString());
        assertEquals(authorBuild.getName(),author.getName());
    }


    @Test
    @DisplayName("When Create Author Valid Request")
    void whenCreateAuthorValidRequest() {
        CreateAuthorRequest request = new CreateAuthorRequest();
        request.setName("test");
        authorService.createAuthor(request);

        ArgumentCaptor<Author> argumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorRepository).save(argumentCaptor.capture());

        Author capturedAuthor = argumentCaptor.getValue();

        assertEquals(request.getName(),capturedAuthor.getName());
    }

    @Test
    @DisplayName("When Create Author Not Valid Request then Thorw Exeption")
    void whenCreateAuthorNotValidRequest_thenThorwExeption() {
        assertThrows(NotValidExeption.class,()-> authorService.createAuthor(new CreateAuthorRequest()));
        verify(authorRepository,never()).save(any());
    }

    @Test
    @DisplayName("When Create Author Not Valid Request then Thorw Exeption")
    void whenCreateAuthorNameEXISTSRequest_thenThorwExeption2() {
        CreateAuthorRequest request = new CreateAuthorRequest("test");
        given(authorRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);
        assertThrows(NotValidExeption.class,()-> authorService.createAuthor(request));
        verify(authorRepository,never()).save(any());
    }

    @Test
    @DisplayName("When Create Author Not Valid Request then Thorw Exeption2")
    void whenCreateAuthorNotValidRequest_thenThorwExeption3() {
        assertThrows(NotValidExeption.class,()-> authorService.createAuthor(new CreateAuthorRequest("")));
        verify(authorRepository,never()).save(any());
    }



    @Test
    @DisplayName("when Update Author Valid Request")
    void whenUpdateAuthorValidRequest() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        Author author = new Author();
        author.setId(id);
        author.setName("testName");
        UpdateAuthorRequest request = new UpdateAuthorRequest("test");
        given(authorRepository.findById(author.getId())).willReturn(Optional.of(author));
        authorService.updateAuthor(id,request);

        ArgumentCaptor<Author> argumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorRepository).findById(id);
        verify(authorRepository).save(argumentCaptor.capture());

        Author capturedAuthor = argumentCaptor.getValue();

        assertEquals(capturedAuthor.getName(),request.getName());
    }

    @Test
    @DisplayName("when Update Author Not Valid Request EMPTY then Throw Exeption")
    void whenUpdateAuthorNotValidRequestEMPTY_thenThrowExeption() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        UpdateAuthorRequest request = new UpdateAuthorRequest("");

        assertThrows(NotValidExeption.class,()->authorService.updateAuthor(id,request));
        verify(authorRepository,never()).save(any());
    }

    @Test
    @DisplayName("when Update Author Not Valid Request NULL then Throw Exeption")
    void whenUpdateAuthorNotValidRequestEXISTS_thenThrowExeption() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        request.setName("test");
        given(authorRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class,()->authorService.updateAuthor(id,request));
        verify(authorRepository,never()).save(any());
    }

    @Test
    @DisplayName("when Update Author Not Valid Request NULL then Throw Exeption")
    void whenUpdateAuthorNotValidRequestNULL_thenThrowExeption() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class,()->authorService.updateAuthor(id,request));
        verify(authorRepository,never()).save(any());
    }

    @Test
    @DisplayName("when delete valid request Author")
    void deleteAuthor() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        Author author = new Author(id,"test", List.of());

        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        authorService.deleteAuthor(id);

        ArgumentCaptor<Author> argumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorRepository).findById(id);
        verify(authorRepository).delete(argumentCaptor.capture());
        Author expected = argumentCaptor.getValue();
        assertEquals(expected,author);
    }

    @Test
    @DisplayName("when get Author By valid Id")
    void getAuthorById() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        Author author = new Author(id,"test", List.of());
        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
    }
}