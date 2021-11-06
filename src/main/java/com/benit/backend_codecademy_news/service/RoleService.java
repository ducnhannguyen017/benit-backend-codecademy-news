package com.benit.backend_codecademy_news.service;

import com.benit.backend_codecademy_news.entity.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role deleteRole(Long roleId) ;
    Role updateRole(Long roleId, Role role) ;
    Role getRoleByName(String roleName) ;
    Role getRoleById(Long id);
    List<Role>getRoles();
    void addRoleToUser(String username, String roleName);
}
