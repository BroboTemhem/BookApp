package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateLanguageRequest;
import com.bookapp.app.dto.requests.UpdateLanguageRequest;
import com.bookapp.app.dto.responses.GetLanguageByIdResponse;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Language;
import com.bookapp.app.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    private LanguageService languageService;

    @Mock
    private LanguageRepository languageRepository;

    @BeforeEach
    void setUp() {
        languageService = new LanguageService(languageRepository);
    }

    @Test
    void whenGetAllLanguageRequest() {
        languageService.getAllLanguage();
        verify(languageRepository).findAll();
    }

    @Test
    void whenCreateLanguageValidRequest() {
        CreateLanguageRequest request = new CreateLanguageRequest();
        request.setName("test");

        languageService.createLanguage(request);

        ArgumentCaptor<Language> argumentCaptor = ArgumentCaptor.forClass(Language.class);
        verify(languageRepository).save(argumentCaptor.capture());
        Language expected = argumentCaptor.getValue();

        assertEquals(expected.getName(),request.getName());
    }

    @Test
    void whenCreateLanguageNameEMPTY_thanThrowExeption() {
        CreateLanguageRequest request = new CreateLanguageRequest();
        request.setName("");

        assertThrows(NotValidExeption.class, ()-> languageService.createLanguage(request));

        verify(languageRepository,never()).save(any());
    }

    @Test
    void whenCreateLanguageNameNULL_thanThrowExeption() {
        CreateLanguageRequest request = new CreateLanguageRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class, ()-> languageService.createLanguage(request));

        verify(languageRepository,never()).save(any());
    }

    @Test
    void whenUpdateLanguageValidRequest() {
        UpdateLanguageRequest request = new UpdateLanguageRequest();
        request.setName("updatedName");
        Integer id = 1;

        Language language = new Language();
        language.setId(id);
        language.setName("test");
        language.setBooks(List.of());

        given(languageRepository.findById(id)).willReturn(Optional.of(language));

        languageService.updateLanguage(id,request);

        verify(languageRepository).findById(id);

        ArgumentCaptor<Language> argumentCaptor = ArgumentCaptor.forClass(Language.class);
        verify(languageRepository).save(argumentCaptor.capture());
        Language expected = argumentCaptor.getValue();

        assertEquals(expected.getName(),request.getName());

    }

    @Test
    void whenUpdateLanguageNameNULL_thanThrowExeption() {
        UpdateLanguageRequest request = new UpdateLanguageRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class, ()-> languageService.updateLanguage(1,request));

        verify(languageRepository,never()).save(any());
    }

    @Test
    void whenUpdateLanguageNameEMPTY_thanThrowExeption() {
        UpdateLanguageRequest request = new UpdateLanguageRequest();
        request.setName("");

        assertThrows(NotValidExeption.class, ()-> languageService.updateLanguage(1,request));

        verify(languageRepository,never()).save(any());
    }

    @Test
    void whenDeleteLanguageRequest() {
        Language language = new Language();
        language.setId(1);
        language.setName("test");
        language.setBooks(List.of());

        given(languageRepository.findById(1)).willReturn(Optional.of(language));

        languageService.deleteLanguage(1);

        ArgumentCaptor<Language> argumentCaptor = ArgumentCaptor.forClass(Language.class);
        verify(languageRepository).delete(argumentCaptor.capture());
        Language expected = argumentCaptor.getValue();

        assertEquals(expected.getName(),language.getName());
        assertEquals(expected.getId(),language.getId());
        assertEquals(expected.getBooks(),language.getBooks());
    }

    @Test
    void whenGetLanguageByIdRequest() {
        Language Language = new Language(1,"test",List.of());
        given(languageRepository.findById(1)).willReturn(Optional.of(Language));
        languageService.getLanguageById(1);
        verify(languageRepository).findById(1);
    }
}