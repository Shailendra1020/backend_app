package com.thenewsgrit.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thenewsgrit.entities.Category;
import com.thenewsgrit.payloads.CategoryDto;

public interface CategoryService {
	
	// create
	 CategoryDto createCategory(CategoryDto categoryDto);
	 
	// update
	 CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	void deleteCategory(Integer categoryId);
	//get
	 CategoryDto getCategory(Integer categoryId);

	//get All
     List<CategoryDto> getCategories();

	
	
}
