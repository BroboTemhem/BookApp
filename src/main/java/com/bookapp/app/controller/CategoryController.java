package com.bookapp.app.controller;

import com.bookapp.app.dto.requests.CreateCategoryRequest;
import com.bookapp.app.dto.requests.UpdateCategoryRequest;
import com.bookapp.app.dto.responses.GetAllCategoryResponse;
import com.bookapp.app.dto.responses.GetCategoryByIdResponse;
import com.bookapp.app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<GetAllCategoryResponse>> getAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategories(), OK);
    }

    @PostMapping
    public ResponseEntity<CreateCategoryRequest> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(createCategoryRequest),CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryRequest> updateCategory(@PathVariable Integer id,
                                                                @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return new ResponseEntity<>(categoryService.updateCategory(id, updateCategoryRequest),CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoryByIdResponse> getCategoryById(@PathVariable Integer id){
        return new ResponseEntity<>(categoryService.getCategoryById(id),OK);
    }

}
