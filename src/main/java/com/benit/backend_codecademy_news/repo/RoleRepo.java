package com.benit.backend_codecademy_news.repo;

import com.benit.backend_codecademy_news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
