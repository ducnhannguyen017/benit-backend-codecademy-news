package com.benit.backend_codecademy_news.controller;

import com.benit.backend_codecademy_news.entity.Category;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/one/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        Category category = categoryService.getCategoryById(id);
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/get-by-tag/{tag}")
    public ResponseEntity<?> getCategoryByTag(@PathVariable("tag") String categoryTag) throws ResourceNotFoundException {
        Category category = categoryService.getCategoryByTag(categoryTag);
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategories() throws ResourceNotFoundException {
        Collection<Category> category = categoryService.getCategories();
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody Category category) throws ResourceNotFoundException {
        Category saveCategory = categoryService.saveCategory(category);
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(saveCategory);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws ResourceNotFoundException {
        Category category = categoryService.deleteCategory(id);
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(category);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody @Valid Category category) throws ResourceNotFoundException {
        Category updateCategory = categoryService.updateCategory(id, category);
        if(category==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(updateCategory);
    }
}
