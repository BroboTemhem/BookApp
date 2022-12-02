package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateLanguageRequest;
import com.bookapp.app.dto.requests.UpdateLanguageRequest;
import com.bookapp.app.dto.responses.GetAllLanguageResponse;
import com.bookapp.app.dto.responses.GetLanguageByIdResponse;
import com.bookapp.app.exeptions.CustomExeption;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Language;
import com.bookapp.app.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<GetAllLanguageResponse> getAllLanguage() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
                .map(language -> {
                    return GetAllLanguageResponse.builder()
                            .id(language.getId())
                            .name(language.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public CreateLanguageRequest createLanguage(CreateLanguageRequest createLanguageRequest) {
        if (createLanguageRequest.getName() == null || createLanguageRequest.getName().trim().isEmpty()){
            throw new NotValidExeption("Language Name not valid!");
        }
        Language createdLanguage = Language.builder()
                .name(createLanguageRequest.getName())
                .build();
        languageRepository.save(createdLanguage);
        return createLanguageRequest;
    }

    public UpdateLanguageRequest updateLanguage(Integer id, UpdateLanguageRequest updateLanguageRequest) {
        if (updateLanguageRequest.getName() == null || updateLanguageRequest.getName().trim().isEmpty()){
            throw new NotValidExeption("Language Name not valid!");
        }
        Language language = languageRepository.findById(id).orElseThrow(() -> new CustomExeption("Language Not Found!"));
        language.setName(updateLanguageRequest.getName());
        languageRepository.save(language);
        return updateLanguageRequest;
    }

    public void deleteLanguage(Integer id) {
        Language language = languageRepository.findById(id).orElseThrow(() -> new CustomExeption("Language Id not found!"));
        languageRepository.delete(language);
    }

    public GetLanguageByIdResponse getLanguageById(Integer id) {
        Language language = languageRepository.findById(id).orElseThrow(() -> new CustomExeption("Language Id not found!"));
        return GetLanguageByIdResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .books(language.getBooks())
                .build();
    }

    protected Language findLanguageByName(String name){
        return languageRepository.findLanguageByName(name)
                .orElseThrow(()-> new CustomExeption("Language name Not Found!"));
    }

}
