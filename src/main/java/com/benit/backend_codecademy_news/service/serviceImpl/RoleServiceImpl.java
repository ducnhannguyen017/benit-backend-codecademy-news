package com.benit.backend_codecademy_news.service.serviceImpl;

import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.entity.Role;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.repo.AppUserRepo;
import com.benit.backend_codecademy_news.repo.RoleRepo;
import com.benit.backend_codecademy_news.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepo roleRepo;
    private final AppUserRepo appUserRepo;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        try{
            return roleRepo.save(role);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role updateRole(Long roleId, Role roleTobeUpdate){
        log.info("Updating role {} ", roleTobeUpdate.getName());
        try{
            Optional<Role> role = roleRepo.findById(roleId);
            if(role.isPresent()){
                roleTobeUpdate.setId(roleId);
                roleRepo.save(roleTobeUpdate);
                return roleRepo.save(roleTobeUpdate);
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role getRoleByName(String roleName){
        try{
            return roleRepo.findByName(roleName);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role getRoleById(Long id){
        try{
            Role role =roleRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found role for this id:" + id));
            return role;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Role> getRoles() {
        log.info("Fetching users");
        try{
            return roleRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role deleteRole(Long roleId){
        try{
            Role role = roleRepo.findById(roleId)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found role for this id:" + roleId));
            roleRepo.delete(role);
            return role;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {} to database", roleName, username);
        AppUser user =appUserRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
