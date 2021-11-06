package com.benit.backend_codecademy_news.dto.user;


import com.benit.backend_codecademy_news.entity.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResponseTokenDto {
    private long id;
    private String username;
    private String name;
    private Collection<String> roles=null;
    private String introduction;
    private String avatar;
    private String accessToken;
    private String refreshToken;

    public ResponseTokenDto(AppUser appUser, String access_token, String refresh_token) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.name = appUser.getName();
        this.roles = appUser.getRoles().stream().map(n->n.getName()).collect(Collectors.toList());
        this.introduction = appUser.getIntroduction();
        this.avatar = appUser.getAvatar();
        this.accessToken =access_token;
        this.refreshToken =refresh_token;
    }
}
