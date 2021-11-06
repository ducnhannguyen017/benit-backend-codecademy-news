package com.benit.backend_codecademy_news.controller;

import com.benit.backend_codecademy_news.CustomResponse;
import com.benit.backend_codecademy_news.entity.Role;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;
import com.benit.backend_codecademy_news.service.RoleService;
import com.benit.backend_codecademy_news.dto.user.RoleToUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/one/{id}")
    public ResponseEntity<?>getUserById( @PathVariable Long id) throws ResourceNotFoundException {
        Role role = roleService.getRoleById(id);
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getRoleByName(@RequestParam("role_name") String roleName) throws ResourceNotFoundException {
        Role role = roleService.getRoleByName(roleName);
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRoles() throws ResourceNotFoundException {
        Collection<Role> role = roleService.getRoles();
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(role);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role) throws ResourceNotFoundException {
        Role saveRole = roleService.saveRole(role);
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(saveRole);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) throws ResourceNotFoundException {
        Role role = roleService.deleteRole(id);
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(role);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid Role role) throws ResourceNotFoundException {
        Role updateRole = roleService.updateRole(id, role);
        if(role==null){
            throw  new ResourceNotFoundException("error");
        }
        return ResponseEntity.ok().body(updateRole);
    }

    @PostMapping("/add-to-user")
    public ResponseEntity<?>saveRole(@Valid @RequestBody RoleToUserDto form){
        roleService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

