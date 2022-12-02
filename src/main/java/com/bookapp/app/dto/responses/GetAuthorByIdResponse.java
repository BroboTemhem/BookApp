package com.bookapp.app.dto.responses;

import com.bookapp.app.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GetAuthorByIdResponse {
    private UUID id;
    private String name;
    private List<Book> books;
}
