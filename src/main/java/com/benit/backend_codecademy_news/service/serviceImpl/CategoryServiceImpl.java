package com.benit.backend_codecademy_news.service.serviceImpl;

import com.benit.backend_codecademy_news.entity.Category;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.repo.CategoryRepo;
import com.benit.backend_codecademy_news.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public Category saveCategory(Category category) {
        log.info("Saving new category {} to database", category.getTag());
        try{
            return categoryRepo.save(category);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryTobeUpdate){
        log.info("Updating category {} ", categoryTobeUpdate.getTag());
        try{
            Optional<Category> category = categoryRepo.findById(categoryId);
            if(category.isPresent()){
                categoryTobeUpdate.setId(categoryId);
                categoryRepo.save(categoryTobeUpdate);
                return categoryRepo.save(categoryTobeUpdate);
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Category getCategoryByTag(String categoryTag){
        try{
            return categoryRepo.findByTag(categoryTag);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Category getCategoryById(Long id){
        try{
            Category category =categoryRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found category for this id:" + id));
            return category;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Category> getCategories() {
        log.info("Fetching users");
        try{
            return categoryRepo.findAllByOrderByNameAsc();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Category deleteCategory(Long categoryId){
        try{
            Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found category for this id:" + categoryId));
            categoryRepo.delete(category);
            return category;
        }catch (Exception e){
            return null;
        }
    }
}
