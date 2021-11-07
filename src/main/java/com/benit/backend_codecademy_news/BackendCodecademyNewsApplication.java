package com.benit.backend_codecademy_news;

import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.service.AppUserService;
import com.benit.backend_codecademy_news.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendCodecademyNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCodecademyNewsApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(AppUserService appUserService, RoleService roleService){
//        return args->{
//            appUserService.saveUser(new AppUser(null, "nhan", "accountadmin", "password","avatar","developer", null));
//            appUserService.saveUser(new AppUser(null, "nhan", "accountuser", "password","avatar","developer",null));
//
//            roleService.addRoleToUser("accountadmin", "ROLE_ADMIN");
//            roleService.addRoleToUser("accountuser", "ROLE_USER");
//            roleService.addRoleToUser("accountadmin", "ROLE_USER");
//
//        };
//    }
}
