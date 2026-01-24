package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @Autowired    // Field Injection
//    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
//        List<Category> categories = categoryService.getAllCategories();
//        return new ResponseEntity<>(categories,HttpStatus.OK);

        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        System.out.println("Inside PostMapping Controller");
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
//        System.out.println("Inside PostMapping");
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
//        return category.getCategoryId() + ", "  + category.getCategoryName() + " " +" Added Successfully..!!";
    }

    @DeleteMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }


    @PutMapping("api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        try {
//            Category updatedCategory = categoryService. (category, categoryId);

//            System.out.println("Inside PutMapping");
            CategoryDTO savedCategotyDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategotyDTO, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(categoryDTO, e.getStatusCode());
        }
    }
}
