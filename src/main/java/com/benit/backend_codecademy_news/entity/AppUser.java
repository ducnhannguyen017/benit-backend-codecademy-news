package com.benit.backend_codecademy_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"posts", "password"})
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL")
    @JsonProperty
    private Long id;

    @JsonProperty
    @Column(name = "name")
    private String name;

    @JsonProperty
    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty
    @Column(nullable = false)
    private String password;

    @JsonProperty
    @Column(name = "avatar")
    private String avatar;

    @JsonProperty
    @Column(columnDefinition = "TEXT")
    private String introduction;

    @JsonProperty
    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name="roles", nullable = false)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles=null;

    @JsonProperty
    @OneToMany(mappedBy = "appUser")
    private Collection<Post> posts=null;

    public AppUser(Long id, String name, String username, String password, String avatar, String introduction, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.introduction = introduction;
        this.roles = roles;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUser(String name, String username, String password, String introduction) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.introduction = introduction;
    }
}
