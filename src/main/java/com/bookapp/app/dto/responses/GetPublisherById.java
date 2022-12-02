package com.bookapp.app.dto.responses;

import com.bookapp.app.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetPublisherById {
    private Integer id;
    private String name;
    private List<Book> books;
}
