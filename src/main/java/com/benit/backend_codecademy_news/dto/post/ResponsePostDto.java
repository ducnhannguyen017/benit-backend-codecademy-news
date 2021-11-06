package com.benit.backend_codecademy_news.dto.post;
import com.benit.backend_codecademy_news.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ResponsePostDto {
    private long id;

    @NotNull(message = "Title is missing")
    private String title;

    @NotNull(message = "Content is missing")
    private String content;

    private Boolean isPublic=true;

    @NotNull(message = "Excerpt is missing")
    private String excerpt;

    private String image;

    private String categoryTag;

    public ResponsePostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.isPublic = post.getIsPublic();
        this.excerpt = post.getExcerpt();
        this.image = post.getImage().getFilename();
        this.categoryTag = post.getCategory().getTag();
    }

}
