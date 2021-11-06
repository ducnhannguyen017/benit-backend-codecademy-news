package com.benit.backend_codecademy_news.repo;

import com.benit.backend_codecademy_news.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByTag(String tag);
    List<Category> findAllByOrderByNameAsc();
}
