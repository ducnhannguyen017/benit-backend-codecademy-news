package com.benit.backend_codecademy_news.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.benit.backend_codecademy_news.constant.JwtConstant;
import com.benit.backend_codecademy_news.dto.user.LoginDto;
import com.benit.backend_codecademy_news.dto.user.ResponseTokenDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper=new ObjectMapper();
        LoginDto loginDto=objectMapper.readValue(request.getInputStream(), LoginDto.class);

        log.info("Username is :{}", loginDto.getUsername());
        log.info("Password is :{}", loginDto.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm= Algorithm.HMAC256(JwtConstant.SECRET_KEY.getBytes());

        String access_token= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtConstant.EXPIRATION_TIME_ACCESS_KEY))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities()
                                        .stream().map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtConstant.EXPIRATION_TIME_REFRESH_KEY))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        AppUser appUser= appUserService.getUserByUsername(user.getUsername());
        ResponseTokenDto responseTokenDto = new ResponseTokenDto(appUser,access_token, refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseTokenDto);
    }
}
