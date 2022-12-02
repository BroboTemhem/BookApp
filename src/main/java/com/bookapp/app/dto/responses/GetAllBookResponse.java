package com.bookapp.app.dto.responses;

import com.bookapp.app.model.Author;
import com.bookapp.app.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GetAllBookResponse {
    private UUID id;
    private String name;
    private String author;
    private String publisher;
    private Double price;

}
