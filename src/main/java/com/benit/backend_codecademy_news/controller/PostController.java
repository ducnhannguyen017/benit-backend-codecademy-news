package com.benit.backend_codecademy_news.controller;

import com.benit.backend_codecademy_news.dto.post.RequestPostDto;
import com.benit.backend_codecademy_news.dto.post.ResponsePostDto;
import com.benit.backend_codecademy_news.entity.Post;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/one/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        Post post = postService.getPostById(id);
        if(post==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/get-by-title")
    public ResponseEntity<?> getPostByTitle(@RequestParam("post_title") String postTitle) throws ResourceNotFoundException {
        Post post = postService.getPostByTitle(postTitle);
        if(post==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<?> getPostByUser(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        List<Post> post = postService.getPostsByUser(userId);
        if(post==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getPostByCategory(@PathVariable("categoryId") Long categoryId) throws ResourceNotFoundException {
        List<Post> post = postService.getPostsByCategory(categoryId);
        if(post==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPosts() {
        Collection<Post> post = postService.getPosts();
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@Valid @RequestBody RequestPostDto postDto) throws ResourceNotFoundException {
        Post savePost =postService.savePost(postDto);
        if(savePost==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(savePost);
    }

    @PostMapping ("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) throws ResourceNotFoundException {
        Post post = postService.deletePost(id);
//        if(post==null){
//            throw  new ResourceNotFoundException("error");
//        }
        return ResponseEntity.ok().body(post);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid RequestPostDto post) throws ResourceNotFoundException {
        Post updatePost = postService.updatePost(id, post);
        if(post==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(updatePost);
    }

}
