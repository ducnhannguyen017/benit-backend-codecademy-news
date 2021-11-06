package com.benit.backend_codecademy_news.controller;

import com.benit.backend_codecademy_news.dto.user.AppUserDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Image;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ImageController {

    private final ImageService imageService ;

    @GetMapping("/list")
    public ResponseEntity<?> getImages() throws ResourceNotFoundException {
        Collection<Image> role = imageService.getImages();
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(role);
    }

    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> upLoadImage(@RequestParam("file") MultipartFile file) throws IOException, ResourceNotFoundException {
        Image image=imageService.uploadImage(file);
        if(image==null){
            throw new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(image);
    }

    @PostMapping(path = "/upload-to-user/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> upLoadImageToUser(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws ResourceNotFoundException{
        AppUser appUser= imageService.uploadAvatarToUser(id, file);
        AppUserDto appUserDto =new AppUserDto(appUser);
        if (appUser==null){
            throw  new ResourceNotFoundException("Not found user by id:"+id);
        }
        return ResponseEntity.ok().body(appUser);
    }
}
