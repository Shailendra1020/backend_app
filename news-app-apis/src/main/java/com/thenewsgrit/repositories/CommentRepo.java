package com.thenewsgrit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thenewsgrit.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	
}
