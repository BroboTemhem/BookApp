package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateBookRequest;
import com.bookapp.app.dto.requests.UpdateBookRequest;
import com.bookapp.app.dto.responses.GetAllBookResponse;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.*;
import com.bookapp.app.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    @Mock
    private LanguageService languageService;
    @Mock
    private PublisherService publisherService;
    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository,
                                        authorService,
                                        languageService,
                                        publisherService,
                                        categoryService);
    }

    @Test
    void whenGetAllBookRequest() {
        bookService.getAllBook();
        verify(bookRepository).findAll();
    }

    @Test
    void whenCreateBookValidRequest() {
        CreateBookRequest request = new CreateBookRequest();
        request.setName("testBookName");
        request.setPrice(123.5);
        request.setPageNumber(111);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");

        Author author = new Author();
        author.setName("testAuthor");

        Category category = new Category();
        category.setName("testCategory");

        Language language = new Language();
        language.setName("testLanguage");

        Publisher publisher = new Publisher();
        publisher.setName("testPublisher");

        given(authorService.findAuthorByName("testAuthor")).willReturn(author);
        given(categoryService.findCategoryByName("testCategory")).willReturn(category);
        given(languageService.findLanguageByName("testLanguage")).willReturn(language);
        given(publisherService.findPublisherByName("testPublisher")).willReturn(publisher);

        bookService.createBook(request);


        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(argumentCaptor.capture());

        Book expected = argumentCaptor.getValue();

        assertEquals(expected.getName(), request.getName());
        assertEquals(expected.getPrice(), request.getPrice());
        assertEquals(expected.getPageNumber(), request.getPageNumber());
        assertEquals(expected.getPopularity(), request.getPopularity());

        assertEquals(expected.getAuthor().getName(), request.getAuthor());
        assertEquals(expected.getCategory().getName(), request.getCategory());
        assertEquals(expected.getLanguage().getName(), request.getLanguage());
        assertEquals(expected.getPublisher().getName(), request.getPublisher());
    }

    @Test
    void whenCreateBookNameNotValidRequest_thenThrowExeption() {
        CreateBookRequest request = new CreateBookRequest();
        request.setName("");
        request.setPrice(123.5);
        request.setPageNumber(111);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");

        assertThrows(NotValidExeption.class, () -> bookService.createBook(request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenCreateBookNameEXISTSRequest_thenThrowExeption() {
        CreateBookRequest request = new CreateBookRequest();
        request.setName("test");
        request.setPrice(123.5);
        request.setPageNumber(111);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        given(bookRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class, () -> bookService.createBook(request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenCreateBookPriceNotValidRequest_thenThrowExeption() {
        CreateBookRequest request = new CreateBookRequest();
        request.setName("testBookName");
        request.setPrice(-1.0);
        request.setPageNumber(111);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");

        assertThrows(NotValidExeption.class, () -> bookService.createBook(request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenCreateBookPageNumberNotValidRequest_thenThrowExeption() {
        CreateBookRequest request = new CreateBookRequest();
        request.setName("testBookName");
        request.setPrice(123.5);
        request.setPageNumber(-123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");

        assertThrows(NotValidExeption.class, () -> bookService.createBook(request));
        verify(bookRepository, never()).save(any());
    }


    @Test
    void whenUpdateBookValidRequest() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName("testBookName");
        request.setPrice(123.5);
        request.setPageNumber(123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");

        Author author = new Author();
        author.setName("testAuthor");

        Category category = new Category();
        category.setName("testCategory");

        Language language = new Language();
        language.setName("testLanguage");

        Publisher publisher = new Publisher();
        publisher.setName("testPublisher");

        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        Book book = new Book();
        book.setId(id);
        book.setName("testBookName");
        book.setPrice(123.5);
        book.setPageNumber(123);
        book.setPopularity(Popularity.GOOD);
        book.setAuthor(author);
        book.setCategory(category);
        book.setLanguage(language);
        book.setPublisher(publisher);

        given(authorService.findAuthorByName("testAuthor")).willReturn(author);
        given(categoryService.findCategoryByName("testCategory")).willReturn(category);
        given(languageService.findLanguageByName("testLanguage")).willReturn(language);
        given(publisherService.findPublisherByName("testPublisher")).willReturn(publisher);
        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        bookService.updateBook(id, request);

        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(argumentCaptor.capture());

        Book expected = argumentCaptor.getValue();

        assertEquals(expected, book);
    }

    @Test
    void whenUpdateBookNameEMPTYValidRequest_thenThrowExeption() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName("");
        request.setPrice(123.5);
        request.setPageNumber(123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        assertThrows(NotValidExeption.class, () -> bookService.updateBook(id, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenUpdateBookNameEXISTSValidRequest_thenThrowExeption() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName("test");
        request.setPrice(123.5);
        request.setPageNumber(123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        given(bookRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class, () -> bookService.updateBook(id, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenUpdateBookNameNULLValidRequest_thenThrowExeption() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName(null);
        request.setPrice(123.5);
        request.setPageNumber(123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        assertThrows(NotValidExeption.class, () -> bookService.updateBook(id, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenUpdateBookPRICEValidRequest_thenThrowExeption() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName("test");
        request.setPrice(-123.5);
        request.setPageNumber(123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        assertThrows(NotValidExeption.class, () -> bookService.updateBook(id, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenUpdateBookPAGENUmBERValidRequest_thenThrowExeption() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setName("test");
        request.setPrice(123.5);
        request.setPageNumber(-123);
        request.setPopularity(Popularity.GOOD);
        request.setAuthor("testAuthor");
        request.setCategory("testCategory");
        request.setLanguage("testLanguage");
        request.setPublisher("testPublisher");
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        assertThrows(NotValidExeption.class, () -> bookService.updateBook(id, request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void whenDeleteBookRequest() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        Book book = new Book();
        book.setId(id);
        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        bookService.deleteBook(id);

        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).delete(argumentCaptor.capture());

        Book expected = argumentCaptor.getValue();

        assertEquals(expected.getId(), book.getId());
    }

    @Test
    void whenGetBookByIdValidRequest() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");

        Author author = new Author();
        author.setName("testAuthor");

        Category category = new Category();
        category.setName("testCategory");

        Language language = new Language();
        language.setName("testLanguage");

        Publisher publisher = new Publisher();
        publisher.setName("testPublisher");

        Book book = new Book();
        book.setId(id);
        book.setName("testBookName");
        book.setPrice(123.5);
        book.setPageNumber(123);
        book.setPopularity(Popularity.GOOD);
        book.setAuthor(author);
        book.setCategory(category);
        book.setLanguage(language);
        book.setPublisher(publisher);

        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        bookService.getBookById(id);

        verify(bookRepository).findById(id);
    }

    @Test
    void getAllBooksByPriceBetween() {
        Double min = 10.0;
        Double max = 100.0;
        bookService.getAllBooksByPriceBetween(min, max);
        verify(bookRepository).findByPriceBetween(min, max);
    }

    @Test
    void getAllBooksByLanguage() {
        String languageName = "test";
        bookService.getAllBooksByLanguage(languageName);
        verify(bookRepository).findByLanguageName(languageName);
    }

    @Test
    void getAllBooksByAuthor() {
        String authorName = "test";
        bookService.getAllBooksByAuthor(authorName);
        verify(bookRepository).findByAuthorName(authorName);
    }

    @Test
    void getAllBooksByPublisher() {
        String publisherName = "test";
        bookService.getAllBooksByPublisher(publisherName);
        verify(bookRepository).findByPublisherName(publisherName);
    }

    @Test
    void getAllBookByCategory() {
        String categoryName = "test";
        bookService.getAllBookByCategory(categoryName);
        verify(bookRepository).findByCategoryName(categoryName);
    }

    @Test
    void getAllBooksMapper() {
        UUID id = UUID.fromString("461319df-80ed-455c-9f7b-63416d75b256");
        Author author = new Author();
        author.setName("testAuthor");

        Category category = new Category();
        category.setName("testCategory");

        Language language = new Language();
        language.setName("testLanguage");

        Publisher publisher = new Publisher();
        publisher.setName("testPublisher");

        Book book = new Book();
        book.setId(id);
        book.setName("testBookName");
        book.setPrice(123.5);
        book.setPageNumber(123);
        book.setPopularity(Popularity.GOOD);
        book.setAuthor(author);
        book.setCategory(category);
        book.setLanguage(language);
        book.setPublisher(publisher);

        List<Book> bookList = List.of(book);

        List<GetAllBookResponse> responses = List.of(
                new GetAllBookResponse(id, "testBookName",
                        "testAuthor",
                        "testPublisher",
                        123.5));


        assertEquals(bookList.get(0).getId(), responses.get(0).getId());
        assertEquals(bookList.get(0).getName(), responses.get(0).getName());
        assertEquals(bookList.get(0).getPrice(), responses.get(0).getPrice());
        assertEquals(bookList.get(0).getPublisher().getName(), responses.get(0).getPublisher());
        assertEquals(bookList.get(0).getAuthor().getName(), responses.get(0).getAuthor());
    }
}