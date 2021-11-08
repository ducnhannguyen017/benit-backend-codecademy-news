package com.benit.backend_codecademy_news.repo;

import com.benit.backend_codecademy_news.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

    @Query(value = "select * from post where user_id=?1 order by id desc", nativeQuery = true)
    List<Post> findByUser(Long userid);

    @Query(value = "select * from post where post.category_id=?1 and post.is_public=true", nativeQuery = true)
    List<Post> findByCategory(Long categoryId);

    @Query(value = "select * from post where post.is_public=true order by id desc", nativeQuery = true)
    List<Post> findAllByOrderByIdDesc();
}
