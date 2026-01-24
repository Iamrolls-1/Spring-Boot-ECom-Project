package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
        {
            return null;
        }

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;

    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        categoryRepository.delete(category);
        return "Category deleted Successfully..!!";
//        return "Category not found..!!";

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

         Optional<Category> categories = categoryRepository.findById(categoryId);
//        Optional<Category> optionalCategory = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst();

//        if (categories.isEmpty()) {
//            throw new ConstraintViolationException("Not Found",);
//        }

        Category categoryToUpdate = categories.get();
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryToUpdate.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRepository.save(categoryToUpdate);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }
}
