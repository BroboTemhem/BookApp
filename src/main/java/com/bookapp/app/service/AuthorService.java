package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateAuthorRequest;
import com.bookapp.app.dto.requests.UpdateAuthorRequest;
import com.bookapp.app.dto.responses.GetAllAuthorResponse;
import com.bookapp.app.dto.responses.GetAuthorByIdResponse;
import com.bookapp.app.exeptions.CustomExeption;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Author;
import com.bookapp.app.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<GetAllAuthorResponse> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> {
                    return GetAllAuthorResponse.builder()
                            .id(author.getId())
                            .name(author.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public CreateAuthorRequest createAuthor(CreateAuthorRequest createAuthorRequest) {
        if (createAuthorRequest.getName() == null || createAuthorRequest.getName().trim().isEmpty()) {
            throw new NotValidExeption("Author Name not valid!");
        }
        Author author = Author.builder()
                .name(createAuthorRequest.getName())
                .build();
        authorRepository.save(author);
        return createAuthorRequest;
    }

    public UpdateAuthorRequest updateAuthor(UUID id, UpdateAuthorRequest updateAuthorRequest) {
        if (updateAuthorRequest.getName() == null || updateAuthorRequest.getName().trim().isEmpty()) {
            throw new NotValidExeption("Author Name not valid!");
        }
        Author author = authorRepository.findById(id).orElseThrow(() -> new CustomExeption("Author Author Not Found!"));
        author.setName(updateAuthorRequest.getName());
        authorRepository.save(author);
        return updateAuthorRequest;
    }

    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new CustomExeption("Author Id not found!"));
        authorRepository.delete(author);
    }

    public GetAuthorByIdResponse getAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new CustomExeption("Author Not Found!"));
        return GetAuthorByIdResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .books(author.getBooks())
                .build();
    }

    protected Author findAuthorByName(String name) {
        return authorRepository.findAuthorByName(name)
                .orElseThrow(() -> new CustomExeption("Author Not Found!"));
    }

}
