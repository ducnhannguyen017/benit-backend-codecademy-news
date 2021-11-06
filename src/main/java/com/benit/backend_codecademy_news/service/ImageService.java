package com.benit.backend_codecademy_news.service;

import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    AppUser uploadAvatarToUser(Long userId, MultipartFile file);

    Image getImageById(Long id);

    String getUrlImage(String name);

    List<Image> getImages();
}
