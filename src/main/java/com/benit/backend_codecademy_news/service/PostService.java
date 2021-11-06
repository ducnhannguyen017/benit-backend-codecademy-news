package com.benit.backend_codecademy_news.service;

import com.benit.backend_codecademy_news.dto.post.RequestPostDto;
import com.benit.backend_codecademy_news.entity.Post;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;

import java.util.List;

public interface PostService {
    Post savePost(RequestPostDto postDto) throws ResourceNotFoundException;
    Post deletePost(Long postId) ;
    Post updatePost(Long postId, RequestPostDto post) throws ResourceNotFoundException;
    Post getPostByTitle(String tile) ;
    Post getPostById(Long id);
    List<Post> getPosts();
    List<Post> getPostsByUser(Long userId);

    List<Post> getPostsByCategory(Long categoryId);
}
