package com.thenewsgrit.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thenewsgrit.entities.Comment;
import com.thenewsgrit.entities.Post;
import com.thenewsgrit.exceptions.ResourceNotFoundException;
import com.thenewsgrit.payloads.CommentDto;
import com.thenewsgrit.repositories.CommentRepo;
import com.thenewsgrit.repositories.PostRepo;
import com.thenewsgrit.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id ",postId) );
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class );
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
				
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment ", "Comment Id ", commentId));
    this.commentRepo.delete(com);
	}

}
