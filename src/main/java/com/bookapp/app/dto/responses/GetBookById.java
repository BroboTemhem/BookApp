package com.bookapp.app.dto.responses;

import com.bookapp.app.model.Popularity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GetBookById {
    private UUID id;
    private String name;
    private String author;
    private String publisher;
    private String language;
    private Integer pageNumber;
    private Double price;
    private Popularity popularity;
    private String category;
}
