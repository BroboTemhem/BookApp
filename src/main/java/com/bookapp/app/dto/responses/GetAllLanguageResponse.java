package com.bookapp.app.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetAllLanguageResponse {
    private Integer id;
    private String name;
}
