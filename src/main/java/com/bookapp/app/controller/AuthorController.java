package com.bookapp.app.controller;

import com.bookapp.app.dto.requests.CreateAuthorRequest;
import com.bookapp.app.dto.requests.UpdateAuthorRequest;
import com.bookapp.app.dto.responses.GetAllAuthorResponse;
import com.bookapp.app.dto.responses.GetAuthorByIdResponse;
import com.bookapp.app.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<GetAllAuthorResponse>> getAllAuthor() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

    @PostMapping
    public ResponseEntity<CreateAuthorRequest> createAuthor(@Valid @RequestBody CreateAuthorRequest createAuthorRequest) {
        return new ResponseEntity<>(authorService.createAuthor(createAuthorRequest), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateAuthorRequest> updateAuthor(@PathVariable UUID id,
                                                            @RequestBody UpdateAuthorRequest updateAuthorRequest) {
        return new ResponseEntity<>(authorService.updateAuthor(id, updateAuthorRequest), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAuthorByIdResponse> getAuthorById(@PathVariable UUID id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), OK);
    }
}
