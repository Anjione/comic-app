package com.example.comicbe.service;

import com.example.comicbe.payload.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> fetchAll();

    CategoryDto insert(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(Long id);
}
