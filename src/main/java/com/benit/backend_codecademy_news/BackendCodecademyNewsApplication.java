package com.benit.backend_codecademy_news;

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
//    CommandLineRunner run(AppUserService appUserService, RoleService roleService,
//                          PostService postService, CategoryService categoryService,
//                          ImageService imageService){
//        return args->{
//            roleService.saveRole(new Role(null, "ROLE_USER","for user",null));
//            roleService.saveRole(new Role(null, "ROLE_ADMIN","for admin",null));
//
//            appUserService.saveUser(new AppUser(null, "nhan", "accountadmin", "password","avatar","developer", null));
//            appUserService.saveUser(new AppUser(null, "nhan", "accountuser", "password","avatar","developer",null));
//
//            roleService.addRoleToUser("accountadmin", "ROLE_ADMIN");
//            roleService.addRoleToUser("accountuser", "ROLE_USER");
//            roleService.addRoleToUser("accountadmin", "ROLE_USER");
//
//
//            categoryService.saveCategory(new Category("Learning Tips", "Learning to code", "learning", "Interested in a career in web development, programming, computer science, or data science?"));
//            categoryService.saveCategory(new Category("Learning Tips", "Learning to code", "learning", "Interested in a career in web development, programming, computer science, or data science?"));
//            categoryService.saveCategory(new Category("Learning Tips", "Learning to code", "learning", "Interested in a career in web development, programming, computer science, or data science?"));
//            categoryService.saveCategory(new Category("Learning Tips", "Learning to code", "learning", "Interested in a career in web development, programming, computer science, or data science?"));
//            categoryService.saveCategory(new Category("Learning Tips", "Learning to code", "learning", "Interested in a career in web development, programming, computer science, or data science?"));
//            postService.savePost(new PostDto( "title","content", true, "nhan", "accountuser",1L, "learning"));

//            appUserService.addUserToRole("accountadmin", "ROLE_ADMIN");
//            appUserService.addUserToRole("accountuser", "ROLE_USER");
//            appUserService.addUserToRole("accountadmin", "ROLE_USER");
//        };
//    }
}
