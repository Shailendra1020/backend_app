 package com.thenewsgrit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thenewsgrit.entities.Post;
import com.thenewsgrit.payloads.PostDto;
import com.thenewsgrit.payloads.PostResponse;

@Service
public interface PostService {

	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete
	
	void deletePost(Integer postId);
	
	// get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);
	
	// get single posts
	
	PostDto getPostById(Integer postId);
	
	// get all post by category
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// get all post by user
	List<PostDto> getPostByUser(Integer userId);
	
	//  search posts 	
	List<PostDto> searchPosts(String keyword);
	
	
	
}
