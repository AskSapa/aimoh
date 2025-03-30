package com.inai.aimoh.controller;

import com.inai.aimoh.dto.RoleDTO;
import com.inai.aimoh.entity.Role;
import com.inai.aimoh.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/search")
    public Role getRoleByName(@RequestParam String name) {
        return roleService.findRoleByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRole(@RequestBody RoleDTO newRole) {
        roleService.createRole(newRole);
        return ResponseEntity.ok("Новый роль успешно создан!");
    }
}
