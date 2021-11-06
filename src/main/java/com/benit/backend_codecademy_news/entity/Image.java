package com.benit.backend_codecademy_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("posts")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    @JsonProperty
    private String filename;

    @OneToMany(mappedBy = "image")
    @JsonProperty
    private Collection<Post> posts=null;

    public Image(String filename) {
        this.filename = filename;
    }
}
