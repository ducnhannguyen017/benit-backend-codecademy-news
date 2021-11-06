package com.benit.backend_codecademy_news.service.serviceImpl;

import com.benit.backend_codecademy_news.dto.post.RequestPostDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Category;
import com.benit.backend_codecademy_news.entity.Image;
import com.benit.backend_codecademy_news.entity.Post;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.repo.*;
import com.benit.backend_codecademy_news.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final CategoryRepo categoryRepo;
    private final AppUserRepo appUserRepo;
    private final PostRepo postRepo;
    private final RoleRepo roleRepo;
    private final ImageRepo imageRepo;

    @Override
    public Post savePost(RequestPostDto postDto) throws ResourceNotFoundException {
        log.info("Saving new post {} to database", postDto.getTitle());
        String username =postDto.getUsername();
        String categoryTag= postDto.getCategoryTag();
        Long imageId= postDto.getImageId();

        AppUser appUser = appUserRepo.findByUsername(username);
        Category category = categoryRepo.findByTag(categoryTag);
        Image image = null;
        if(imageId==null){
            image= imageRepo.findFirstByOrderByIdAsc();
        }else{
            image= imageRepo.findById(imageId).orElseThrow(()-> new ResourceNotFoundException("not found"));
        }

        Post post = new Post(postDto.getId(), postDto.getTitle(),postDto.getContent(),
                    postDto.getIsPublic(), postDto.getExcerpt(),
                    appUser,image, category);
        try{
            return postRepo.save(post);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Post updatePost(Long postId, RequestPostDto postTobeUpdate) throws ResourceNotFoundException {
        log.info("Updating post {} ", postTobeUpdate.getTitle());
        String username =postTobeUpdate.getUsername();
        String categoryTag= postTobeUpdate.getCategoryTag();
        Long imageId= postTobeUpdate.getImageId();

        AppUser appUser = appUserRepo.findByUsername(username);
        Category category = categoryRepo.findByTag(categoryTag);
        Image image =imageRepo.findById(imageId).orElseThrow(()-> new ResourceNotFoundException("not found"));

        Post post = new Post(postTobeUpdate.getTitle(),postTobeUpdate.getContent(),
                postTobeUpdate.getIsPublic(), postTobeUpdate.getExcerpt(),
                appUser,image, category);
        try{
            if(postRepo.findById(postId).isPresent()){
                post.setId(postId);
                return postRepo.save(post);
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Post getPostByTitle(String postName){
        try{
            return postRepo.findByTitle(postName);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Post getPostById(Long id){
        try{
            Post post =postRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found post for this id:" + id));
            return post;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Post> getPosts() {
        log.info("Fetching users");
        try{
            return postRepo.findAllByOrderByIdDesc();
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public Post deletePost(Long postId){
        try{
            Post post = postRepo.findById(postId)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found post for this id:" + postId));
            postRepo.delete(post);
            return post;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Post> getPostsByUser(Long userid){
        try{
            return postRepo.findByUser(userid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        try{
            return postRepo.findByCategory(categoryId);
        }catch (Exception e){
            return null;
        }
    }
}
