package com.benit.backend_codecademy_news.repo;

import com.benit.backend_codecademy_news.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);

    @Override
    @Query(value = "select * from app_user", nativeQuery = true)
    List<AppUser> findAll();

}
