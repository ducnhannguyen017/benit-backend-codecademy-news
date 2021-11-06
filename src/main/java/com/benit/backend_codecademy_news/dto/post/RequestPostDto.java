package com.benit.backend_codecademy_news.dto.post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RequestPostDto {
    private long id;

    @NotNull(message = "Title is missing")
    private String title;

    @NotNull(message = "Content is missing")
    private String content;

    private Boolean isPublic=true;

    @NotNull(message = "Excerpt is missing")
    private String excerpt;

    private String username;

    private Long imageId;

    private String categoryTag;

    public RequestPostDto(String title, String content, Boolean isPublic, String excerpt, String appUser, Long imageId, String category) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.excerpt = excerpt;
        this.username = appUser;
        this.imageId = imageId;
        this.categoryTag = category;
    }

    public RequestPostDto(String title, String content, Boolean isPublic, String excerpt, String appUser, String category) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.excerpt = excerpt;
        this.username = appUser;
        this.categoryTag = category;
    }
}
