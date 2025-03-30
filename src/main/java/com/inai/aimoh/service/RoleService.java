package com.inai.aimoh.service;

import com.inai.aimoh.dto.RoleDTO;
import com.inai.aimoh.entity.Role;
import com.inai.aimoh.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;




    /**
     * Метод для создания ролей.
     * Этим метод доступен только админам.
     */

    @Transactional
    public Role createRole(RoleDTO roleDto) {
        if (roleRepository.existsByName(roleDto.name())) {
            throw new IllegalArgumentException("Такая роль уже есть!");
        }
        Role role = new Role();
        role.setName(roleDto.name());
        return roleRepository.save(role); // Потом эту штуку что-то нужно сделать
    }




    /**
     * Метод для поиска всех ролей.
     */

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }




    /**
     * Метод для поиска ролей по названию роля.
     */

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }




    /**
     * Метод для поиска ролей по id роля.
     */

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
