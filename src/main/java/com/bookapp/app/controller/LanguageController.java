package com.bookapp.app.controller;

import com.bookapp.app.dto.requests.CreateLanguageRequest;
import com.bookapp.app.dto.requests.UpdateLanguageRequest;
import com.bookapp.app.dto.responses.GetAllLanguageResponse;
import com.bookapp.app.dto.responses.GetLanguageByIdResponse;
import com.bookapp.app.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<GetAllLanguageResponse>> getAllLanguages() {
        return new ResponseEntity<>(languageService.getAllLanguage(), OK);
    }

    @PostMapping
    public ResponseEntity<CreateLanguageRequest> createLanguage(@RequestBody CreateLanguageRequest createLanguageRequest) {
        return new ResponseEntity<>(languageService.createLanguage(createLanguageRequest), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateLanguageRequest> updateLanguage(@PathVariable Integer id,
                                                                @RequestBody UpdateLanguageRequest updateLanguageRequest) {
        return new ResponseEntity<>(languageService.updateLanguage(id, updateLanguageRequest), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguageById(@PathVariable Integer id) {
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLanguageByIdResponse> getLanguageById(@PathVariable Integer id) {
        return new ResponseEntity<>(languageService.getLanguageById(id), OK);
    }
}
