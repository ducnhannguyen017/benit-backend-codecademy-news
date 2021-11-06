package com.benit.backend_codecademy_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "categories"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL")
    @JsonProperty
    private long id;

    @Column(name = "title")
    @JsonProperty
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    @JsonProperty
    private String content;

    @Column(name = "is_public")
    @JsonProperty
    private Boolean isPublic=true;

    @Column(name = "excerpt",columnDefinition = "TEXT")
    @JsonProperty
    private String excerpt;

    @Column(name = "created_at")
    @JsonProperty
    @CreationTimestamp
    private Date createdAt ;

    @Column(name = "updated_at")
    @JsonProperty
    @UpdateTimestamp
    private Date updatedAt ;

    @ManyToOne
    @JoinColumn(name="user_id" , columnDefinition = "INT NULL")
    @JsonProperty
    private AppUser appUser=null;

    @ManyToOne
    @JoinColumn(name="image_id", columnDefinition = "INT NULL")
    @JsonProperty
    private Image image=null;

    @ManyToOne
    @JoinColumn(name="category_id", columnDefinition = "INT NULL")
    @JsonProperty
    private Category category =null;

    public Post( String title, String content,Boolean isPublic, String excerpt, AppUser appUser, Image image, Category category) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.excerpt = excerpt;
        this.appUser = appUser;
        this.image = image;
        this.category = category;
    }

    public Post( String title, String content, Boolean isPublic, String excerpt, AppUser appUser, Category category) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.excerpt = excerpt;
        this.appUser = appUser;
        this.category = category;
    }

    public Post(long id, String title, String content, Boolean isPublic, String excerpt, AppUser appUser, Image image, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.excerpt = excerpt;
        this.appUser = appUser;
        this.image = image;
        this.category = category;
    }
}
