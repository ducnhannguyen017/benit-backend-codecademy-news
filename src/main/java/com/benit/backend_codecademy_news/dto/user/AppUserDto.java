package com.benit.backend_codecademy_news.dto.user;

import com.benit.backend_codecademy_news.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AppUserDto {
    private long id;

    @NotNull(message = "Username is missing")
    @Pattern(
            message = "Invalid appUsername",
            regexp = "^(?!default$)([a-z0-9]+)$"
    )
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password is missing")
    private String password;

    @NotNull(message = "First name is missing")
    private String name;

    private Collection<String> roles;

    @NotNull(message = "Introduction cannot be null")
    private String introduction;

    private String avatar;

    public AppUserDto(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.name = appUser.getName();
        this.roles = appUser.getRoles().stream().map(n->n.getName()).collect(Collectors.toList());
        this.introduction = appUser.getIntroduction();
        this.avatar = Optional.ofNullable(appUser.getAvatar()).orElse("default.jpg");
    }
}
