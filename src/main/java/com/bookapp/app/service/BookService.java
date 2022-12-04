package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateBookRequest;
import com.bookapp.app.dto.requests.UpdateBookRequest;
import com.bookapp.app.dto.responses.GetAllBookResponse;
import com.bookapp.app.dto.responses.GetBookById;
import com.bookapp.app.exeptions.CustomExeption;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.*;
import com.bookapp.app.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final LanguageService languageService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;


    public List<GetAllBookResponse> getAllBook() {
        List<Book> bookList = bookRepository.findAll();
        return getAllBooksMapper(bookList);
    }

    public CreateBookRequest createBook(CreateBookRequest createBookRequest) {
        String bookName = createBookRequest.getName();
        if (bookName == null || bookName.trim().isEmpty()) {
            throw new NotValidExeption("Book name not valid!");
        }
        if (bookRepository.existsByNameContainingIgnoreCase(bookName)){
            throw new NotValidExeption("Book name already exists!");
        }
        if (createBookRequest.getPrice() < 0) {
            throw new NotValidExeption("Price not valid!");
        }
        if (createBookRequest.getPageNumber() < 0) {
            throw new NotValidExeption("Page number not valid!");
        }

        Category categoryByName = categoryService.findCategoryByName(createBookRequest.getCategory());
        Author authorByName = authorService.findAuthorByName(createBookRequest.getAuthor());
        Language languageByName = languageService.findLanguageByName(createBookRequest.getLanguage());
        Publisher publisherByName = publisherService.findPublisherByName(createBookRequest.getPublisher());

        Book createdBook = Book.builder()
                .name(createBookRequest.getName())
                .price(createBookRequest.getPrice())
                .pageNumber(createBookRequest.getPageNumber())
                .popularity(createBookRequest.getPopularity())
                .category(categoryByName)
                .author(authorByName)
                .language(languageByName)
                .publisher(publisherByName)
                .build();

        bookRepository.save(createdBook);
        return createBookRequest;
    }

    public UpdateBookRequest updateBook(UUID id, UpdateBookRequest updateBookRequest) {
        String updatedBookName = updateBookRequest.getName();
        if (updatedBookName == null || updatedBookName.trim().isEmpty()){
            throw new NotValidExeption("Update Book Name is Not Valid!");
        }
        if (bookRepository.existsByNameContainingIgnoreCase(updatedBookName)){
            throw new NotValidExeption("Update Book Name is already exists!");
        }
        if (updateBookRequest.getPrice() < 0){
            throw new NotValidExeption("Update Book Price is Not Valid!");
        }
        if (updateBookRequest.getPageNumber() < 0){
            throw new NotValidExeption("Update Book Page is Not Valid!");
        }

        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomExeption("Id not found!"));

        Author newAuthor = authorService.findAuthorByName(updateBookRequest.getAuthor());
        Publisher newPublisher = publisherService.findPublisherByName(updateBookRequest.getPublisher());
        Language newLanguage = languageService.findLanguageByName(updateBookRequest.getLanguage());
        Category newCategory = categoryService.findCategoryByName(updateBookRequest.getCategory());

        book.setAuthor(newAuthor);
        book.setLanguage(newLanguage);
        book.setPublisher(newPublisher);
        book.setCategory(newCategory);
        book.setName(updateBookRequest.getName());
        book.setPrice(updateBookRequest.getPrice());
        book.setPopularity(updateBookRequest.getPopularity());
        book.setPageNumber(updateBookRequest.getPageNumber());

        bookRepository.save(book);
        return updateBookRequest;
    }

    public void deleteBook(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomExeption("Id not found!"));
        bookRepository.delete(book);
    }

    public GetBookById getBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomExeption("Id not found!"));
        return GetBookById.builder()
                .id(book.getId())
                .name(book.getName())
                .price(book.getPrice())
                .popularity(book.getPopularity())
                .pageNumber(book.getPageNumber())
                .author(book.getAuthor().getName())
                .category(book.getCategory().getName())
                .language(book.getLanguage().getName())
                .publisher(book.getPublisher().getName())
                .build();
    }

    public List<GetAllBookResponse> getAllBooksByPriceBetween(Double minPrice, Double maxPrice) {
        List<Book> bookList = bookRepository.findByPriceBetween(minPrice, maxPrice);
        return getAllBooksMapper(bookList);
    }

    public List<GetAllBookResponse> getAllBooksByLanguage(String languageName) {
        List<Book> bookList = bookRepository.findByLanguageName(languageName);
        return getAllBooksMapper(bookList);
    }

    public List<GetAllBookResponse> getAllBooksByAuthor(String authorName) {
        List<Book> bookList = bookRepository.findByAuthorName(authorName);
        return getAllBooksMapper(bookList);
    }

    public List<GetAllBookResponse> getAllBooksByPublisher(String publisherName) {
        List<Book> bookList = bookRepository.findByPublisherName(publisherName);
        return getAllBooksMapper(bookList);
    }

    public List<GetAllBookResponse> getAllBookByCategory(String categoryName) {
        List<Book> bookList = bookRepository.findByCategoryName(categoryName);
        return getAllBooksMapper(bookList);
    }


    /*
    GetAllBookResponse dto using 6 different place with getAllBooksMapper.
    -getAllBookByCategory()
    -getAllBooksByPublisher()
    -getAllBooksByAuthor()
    -getAllBooksByLanguage()
    -getAllBooksByPriceBetween()
    -getAllBook()
    */
    public List<GetAllBookResponse> getAllBooksMapper(List<Book> bookList) {
        return bookList.stream()
                .map(book -> {
                    return GetAllBookResponse.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .price(book.getPrice())
                            .author(book.getAuthor().getName())
                            .publisher(book.getPublisher().getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
