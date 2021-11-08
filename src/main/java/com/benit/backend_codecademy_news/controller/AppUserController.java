package com.benit.backend_codecademy_news.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.benit.backend_codecademy_news.CustomResponse;
import com.benit.backend_codecademy_news.constant.JwtConstant;
import com.benit.backend_codecademy_news.dto.user.AppUserDto;
import com.benit.backend_codecademy_news.dto.user.LoginDto;
import com.benit.backend_codecademy_news.dto.user.SignUpDto;
import com.benit.backend_codecademy_news.dto.user.UpdateUserDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Role;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.service.AppUserService;
import com.benit.backend_codecademy_news.service.RoleService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AppUserController {
    private final AppUserService appUserService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final Environment env;


    @GetMapping("/list")
    public ResponseEntity<?> getUsers() throws ResourceNotFoundException {
        Collection<AppUser> appUsers = appUserService.getUsers();
        Collection<AppUserDto> appUserDto =appUsers.stream().map(AppUserDto::new).collect(Collectors.toList());
        if(appUsers==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(appUsers);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?>getUserById( @PathVariable Long id) throws ResourceNotFoundException {
        AppUser appUser = appUserService.getUserById(id);
        AppUserDto appUserDto=new AppUserDto(appUser);
        if(appUser==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(appUser);
    }

    //Save User
    @PostMapping("/save")
    public ResponseEntity<?>saveUser(@Valid @RequestBody SignUpDto signUpDto) throws ResourceNotFoundException {
        AppUser savedAppUser = appUserService.saveUser(signUpDto);
        if(savedAppUser==null){
            throw  new ResourceNotFoundException("This user existed");
        }
        return ResponseEntity.ok().body(savedAppUser);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) throws ResourceNotFoundException {
        AppUser updateUser = appUserService.updateUser(id, updateUserDto);
        if(updateUser==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(updateUser);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        AppUser updateUser = appUserService.deleteUser(id);
        if(updateUser==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(updateUser);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null&&authorizationHeader.startsWith(JwtConstant.TOKEN_PREFIX)){
            try {
                //detach token
                String refresh_token =authorizationHeader.substring(JwtConstant.TOKEN_PREFIX.length());
                Algorithm algorithm= Algorithm.HMAC256(JwtConstant.SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT= verifier.verify(refresh_token);

                //get infor in token
                String username = decodedJWT.getSubject();
                AppUser appUser = appUserService.getUserByUsername(username);

                String access_token= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ JwtConstant.EXPIRATION_TIME_ACCESS_KEY))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getRoles()
                                .stream().map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());

                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }else {
            throw new RuntimeException("refresh token is missing");
        }
    }


}

