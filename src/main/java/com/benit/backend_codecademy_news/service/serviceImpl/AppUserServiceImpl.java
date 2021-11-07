package com.benit.backend_codecademy_news.service.serviceImpl;

import com.benit.backend_codecademy_news.dto.user.SignUpDto;
import com.benit.backend_codecademy_news.dto.user.UpdateUserDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Image;
import com.benit.backend_codecademy_news.entity.Role;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.repo.AppUserRepo;
import com.benit.backend_codecademy_news.repo.ImageRepo;
import com.benit.backend_codecademy_news.repo.RoleRepo;
import com.benit.backend_codecademy_news.service.AppUserService;
import com.benit.backend_codecademy_news.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService , UserDetailsService {


    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final ImageRepo imageRepo;
    private final PasswordEncoder passwordEncoder;
    private  final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=appUserRepo.findByUsername(username);
        if(appUser==null){
            log.error("User not fond in database");
            throw  new UsernameNotFoundException("User not font in database");
        }
        else{
            log.info("User found in database:{}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority((role.getName())));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(SignUpDto signUpDto) {
        log.info("Saving new user {} to database", signUpDto.getUsername());
        try{
            signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Set<Role> roles = new HashSet<Role>();
            roles.add(roleRepo.findByName("ROLE_USER"));

            Image image = imageRepo.findById(1L).orElseThrow(
                    ()->new ResourceNotFoundException("not found")
            );
            AppUser appUser = new AppUser(signUpDto.getUsername(), signUpDto.getPassword());
            appUser.setRoles(roles);
            appUser.setAvatar(image.getFilename());
            return  appUserRepo.save(appUser);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public AppUser getUserByUsername(String username) {
        log.info("Fetching user {}", username);
        try {
            return appUserRepo.findByUsername(username);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching users");
        try {
            return appUserRepo.findAll();
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public AppUser deleteUser(Long id) {
        try {
            AppUser appUser = appUserRepo.findById(id).orElseThrow(
                    ()-> new ResourceNotFoundException("not found")
            );
            appUserRepo.delete(appUser);
            return appUser;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public AppUser updateUser(Long id, UpdateUserDto updateUserDto) {
        log.info("Updating role {} ", updateUserDto.getName());
        try{
            AppUser appUserToUpdate = appUserRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
            Role role= roleRepo.findByName(updateUserDto.getRole());
            appUserToUpdate.setName(updateUserDto.getName());
            appUserToUpdate.setIntroduction(updateUserDto.getIntroduction());
            appUserToUpdate.getRoles().add(role);
            return appUserToUpdate;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public AppUser getUserById(Long id) {
        log.info("Fetching user {}", id);
        try {
            AppUser appUser = appUserRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not Found"));
            return appUser;
        }catch (Exception e) {
            return null;
        }
    }
}
