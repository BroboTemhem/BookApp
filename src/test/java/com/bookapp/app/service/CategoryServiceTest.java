package com.bookapp.app.service;

import com.bookapp.app.dto.requests.CreateCategoryRequest;
import com.bookapp.app.dto.requests.UpdateCategoryRequest;
import com.bookapp.app.dto.responses.GetAllCategoryResponse;
import com.bookapp.app.exeptions.NotValidExeption;
import com.bookapp.app.model.Category;
import com.bookapp.app.repository.CategoryRepository;
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
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void whenGetAllCategoriesRequest() {
        GetAllCategoryResponse test = GetAllCategoryResponse.builder().id(1).name("test").build();

        categoryService.getAllCategories();
        verify(categoryRepository).findAll();

        assertEquals(test.getId(), 1);
        assertEquals(test.getName(), "test");
    }

    @Test
    void whenCreateCategoryValidRequest() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("test");

        categoryService.createCategory(request);
        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(argumentCaptor.capture());
        Category expected = argumentCaptor.getValue();

        assertEquals(expected.getName(), request.getName());
    }

    @Test
    void whenCreateCategoryNameEMPTYRequest_thenThrowExeption() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("");

        assertThrows(NotValidExeption.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void whenCreateCategoryNameEXISTSRequest_thenThrowExeption() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("test");
        given(categoryRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void whenCreateCategoryNameNULLRequest_thenThrowExeption() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void whenUpdateCategoryValidRequst() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName("test");

        Category category = new Category(1,"test", List.of());

        given(categoryRepository.findById(1)).willReturn(Optional.of(category));

        categoryService.updateCategory(1,request);

        verify(categoryRepository).findById(1);

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(argumentCaptor.capture());
        Category expected = argumentCaptor.getValue();

        assertEquals(expected.getName(),request.getName());
    }

    @Test
    void whenUpdateCategoryNameNULLRequst_thenThrowExeption() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName(null);

        assertThrows(NotValidExeption.class,()-> categoryService.updateCategory(1,request));

        verify(categoryRepository,never()).save(any());
    }

    @Test
    void whenUpdateCategoryEXISTSNULLRequst_thenThrowExeption() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName("test");
        given(categoryRepository.existsByNameContainingIgnoreCase(request.getName())).willReturn(true);

        assertThrows(NotValidExeption.class,()-> categoryService.updateCategory(1,request));

        verify(categoryRepository,never()).save(any());
    }

    @Test
    void whenUpdateCategoryNameEMPTYRequst_thenThrowExeption() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName("");

        assertThrows(NotValidExeption.class,()-> categoryService.updateCategory(1,request));

        verify(categoryRepository,never()).save(any());
    }

    @Test
    void whenDeleteCategoryRequest() {
        Category category = new Category(1,"test", List.of());
        given(categoryRepository.findById(1)).willReturn(Optional.of(category));

        categoryService.deleteCategory(1);

        verify(categoryRepository).delete(category);
    }

    @Test
    void whenGetCategoryByIdRequest() {
        Category category = new Category(1,"test", List.of());
        given(categoryRepository.findById(1)).willReturn(Optional.of(category));

        categoryService.getCategoryById(1);

        verify(categoryRepository).findById(1);
    }
}