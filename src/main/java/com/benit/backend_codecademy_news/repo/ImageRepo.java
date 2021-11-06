package com.benit.backend_codecademy_news.repo;

import com.benit.backend_codecademy_news.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image,Long> {
    Image findFirstByOrderByIdAsc();
}
