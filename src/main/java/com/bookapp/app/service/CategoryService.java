package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateCategoryRequest;
import com.bookapp.app.dto.requests.UpdateCategoryRequest;
import com.bookapp.app.dto.responses.GetAllCategoryResponse;
import com.bookapp.app.dto.responses.GetCategoryByIdResponse;
import com.bookapp.app.exeptions.CustomExeption;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Category;
import com.bookapp.app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<GetAllCategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> {
                    return GetAllCategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public CreateCategoryRequest createCategory(CreateCategoryRequest createCategoryRequest) {
        if (createCategoryRequest.getName() == null || createCategoryRequest.getName().trim().isEmpty()){
            throw new NotValidExeption("Category Name not valid!");
        }
        Category createdCategory = Category.builder()
                .name(createCategoryRequest.getName())
                .build();
        categoryRepository.save(createdCategory);
        return createCategoryRequest;
    }

    public UpdateCategoryRequest updateCategory(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        if (updateCategoryRequest.getName() == null || updateCategoryRequest.getName().trim().isEmpty()){
            throw new NotValidExeption("Category Name not valid!");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Category Id not found!"));
        category.setName(updateCategoryRequest.getName());
        categoryRepository.save(category);
        return updateCategoryRequest;
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Category Id notfound!"));
        categoryRepository.delete(category);
    }

    public GetCategoryByIdResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Category Id not found!"));
        return GetCategoryByIdResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .bookList(category.getBooks())
                .build();
    }

    protected Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new CustomExeption("Category Name Not found!"));
    }


}
