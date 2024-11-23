package com.thenewsgrit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thenewsgrit.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
