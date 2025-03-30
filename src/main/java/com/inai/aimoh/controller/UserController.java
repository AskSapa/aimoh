package com.inai.aimoh.controller;

import com.inai.aimoh.dto.AdminCreateOrEditUserDTO;
import com.inai.aimoh.entity.User;
import com.inai.aimoh.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUserByAdmin(@RequestBody AdminCreateOrEditUserDTO newUser) {
        userService.createUserByAdmin(newUser);
        return ResponseEntity.ok("Аккаунт сотрудника успешно создан!");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable Long id) {
        userService.deleteUserByAdmin(id);
        return ResponseEntity.ok("Пользователь помечен как удаленный!");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editUserByAdmin(@PathVariable Long id, @RequestBody AdminCreateOrEditUserDTO editUser) {
        userService.updateUserByAdmin(id, editUser);
        return ResponseEntity.ok("Пользователь успешно отредактирован!");
    }
}
