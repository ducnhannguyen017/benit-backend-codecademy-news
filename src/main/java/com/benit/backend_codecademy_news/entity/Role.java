package com.benit.backend_codecademy_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("users")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty
    private Long id;

    @Column(name="name", unique = true)
    @JsonProperty
    @NotNull(message = "Role name is missing")
    private String name;

    @Column(name="description")
    @JsonProperty
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonProperty
    private Collection<AppUser> users=null;

}
