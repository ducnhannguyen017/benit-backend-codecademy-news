package com.benit.backend_codecademy_news.service.serviceImpl;

import com.benit.backend_codecademy_news.config.FirebaseConfig;
import com.benit.backend_codecademy_news.constant.FirebaseConstant;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Image;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.repo.AppUserRepo;
import com.benit.backend_codecademy_news.repo.ImageRepo;
import com.benit.backend_codecademy_news.service.ImageService;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final AppUserRepo appUserRepo;
    private final ImageRepo imageRepo;
    private final FirebaseConfig firebaseConfig;

    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        try{
            String filename=saveImage(file);
            Image image=new Image(filename);
            imageRepo.save(image);
            return image;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public AppUser uploadAvatarToUser(Long userId, MultipartFile file) {
        try{
            AppUser appUser = appUserRepo.findById(userId).orElseThrow(
                    ()->new ResourceNotFoundException("Not found user with id:"+userId));
            appUser.setId(userId);
            appUser.setAvatar(saveImage(file));
            return appUserRepo.save(appUser);
        } catch (ResourceNotFoundException | IOException e) {
            return null;
        }
    }

    @Override
    public Image getImageById(Long id){
        try{
            Image image =imageRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found image for this id:" + id));
            return image;
        }catch (Exception e){
            return null;
        }
    }

    private String saveImage(MultipartFile file) throws IOException{
        Bucket bucket = StorageClient.getInstance().bucket();
        String imageName = generateFileName(file.getOriginalFilename());
        bucket.create(imageName, file.getBytes(), file.getContentType());
        return getUrlImage(imageName);
    }

    public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }
        Blob blob = bucket.get(name);
        if (blob == null) {
            throw new IOException("file not found");
        }
        blob.delete();
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    @Override
    public String getUrlImage(String name){
        return "https://firebasestorage.googleapis.com/v0/b/benit-codecademy-news-app.appspot.com/o/"+name+"?alt=media&token="+name;
    }

    @Override
    public List<Image> getImages() {
        log.info("Fetching users");
        try{
            return imageRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

}
