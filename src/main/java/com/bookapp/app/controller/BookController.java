package com.bookapp.app.controller;

import com.bookapp.app.dto.requests.CreateBookRequest;
import com.bookapp.app.dto.requests.UpdateBookRequest;
import com.bookapp.app.dto.responses.GetAllBookResponse;
import com.bookapp.app.dto.responses.GetBookById;
import com.bookapp.app.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    @GetMapping
    public ResponseEntity<List<GetAllBookResponse>> getAllBook() {
        return new ResponseEntity<>(bookService.getAllBook(), OK);
    }

    @PostMapping
    public ResponseEntity<CreateBookRequest> createBook(@RequestBody CreateBookRequest createBookRequest) {
        return new ResponseEntity<>(bookService.createBook(createBookRequest), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateBookRequest> updateBook(@PathVariable UUID id,
                                                        @RequestBody UpdateBookRequest updateBookRequest) {
        return new ResponseEntity<>(bookService.updateBook(id, updateBookRequest), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBookById> getBookById(@PathVariable UUID id){
        return new ResponseEntity<>(bookService.getBookById(id),OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<GetAllBookResponse>> getAllBooksByPriceBetween(@RequestParam Double minPrice, Double maxPrice){
        return new ResponseEntity<>(bookService.getAllBooksByPriceBetween(minPrice, maxPrice),OK);
    }

    @GetMapping("/language")
    public ResponseEntity<List<GetAllBookResponse>> getAllBooksByLanguage(@RequestParam String name){
        return new ResponseEntity<>(bookService.getAllBooksByLanguage(name),OK);
    }

    @GetMapping("/author")
    public ResponseEntity<List<GetAllBookResponse>> getAllBooksByAuthor(@RequestParam String name){
        return new ResponseEntity<>(bookService.getAllBooksByAuthor(name),OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<GetAllBookResponse>> getAllBooksByPublisher(@RequestParam String name){
        return new ResponseEntity<>(bookService.getAllBooksByPublisher(name),OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<GetAllBookResponse>> getAllBookByCategory(@RequestParam String name){
        return new ResponseEntity<>(bookService.getAllBookByCategory(name),OK);
    }
}
