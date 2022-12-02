package com.bookapp.app.dto.requests;

import com.bookapp.app.model.Popularity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRequest {
    private String name;
    private String author;
    private String publisher;
    private String language;
    private Integer pageNumber;
    private Double price;
    private Popularity popularity;
    private String category;
}
