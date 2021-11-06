package com.benit.backend_codecademy_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("posts")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private Long id;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @Column(name = "title")
    @JsonProperty
    private String title;

    @Column(name = "tag",unique = true, nullable = false)
    @JsonProperty
    private String tag;

    @Column(name = "description",columnDefinition = "TEXT")
    @JsonProperty
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonProperty
    private Collection<Post> posts=null;

    public Category( String name, String title, String tag, String description) {
        this.name = name;
        this.title = title;
        this.tag = tag;
        this.description = description;
    }
}
