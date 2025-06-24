package com.inai.aimoh.service;

import com.inai.aimoh.dto.AdminCreateOrEditUserDTO;
import com.inai.aimoh.entity.User;
import com.inai.aimoh.entity.emun.Role;
import com.inai.aimoh.entity.emun.UserStatus;
import com.inai.aimoh.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    /**
     * Этот метод для создания аккаунтов сотрудников администратором.
     * Здесь админ не может создать аккаунт для гостей.
     * Совпадения login или email с login или email уже существующим пользователем не должны
     */

    @Transactional
    public void createUserByAdmin(AdminCreateOrEditUserDTO request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new IllegalArgumentException("Логин уже занят!");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email уже зарегистрирован!");
        }
        Role role;
        try {
             role = Role.valueOf(request.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неправильно введенный роль: " + request.role());
        }
        if ("GUEST".equals(request.role())) {
            throw new IllegalArgumentException("Админ не может создать гостя");
        }
        User user = new User();
        user.setLogin(request.login());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(role);
        userRepository.save(user);
    }




    /**
     * Метод для "удаления" аккаунтов сотрудников отелей и гостей.
     * Здесь не совсем удаляются аккаунты, а просто столбец @is_deleted меняется с false на true.
     */

    @Transactional
    public void deleteUserByAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        if ("ADMIN".equals(user.getRole().name())) {
            throw new IllegalArgumentException("Нельзя удалить другого администратора!");
        }
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }




    /**
     * Поиск всех аккаунтов всех ролей.
     */

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }




    /**
     * Сам метод является для редактирования данных пользователей администратором.
     * @param userId это id пользователя, которого хочет изменить данные админ.
     * @param request это данные, на которую хочет изменить админ.
     */

    @Transactional
    public void updateUserByAdmin(Long userId, AdminCreateOrEditUserDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        if ("ADMIN".equals(user.getRole().name())) {
            throw new IllegalArgumentException("Нельзя изменить данные другого администратора!");
        }
        if (userRepository.existsByLogin(request.login())
                && !(user.getLogin().equals(request.login()))) {
            throw new IllegalArgumentException("Логин уже занят!");
        }
        if (userRepository.existsByEmail(request.email())
                && !(user.getEmail().equals(request.email()))) {
            throw new IllegalArgumentException("Email уже зарегистрирован!");
        }
        user.setLogin(request.login());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        Role role;
        try {
            role = Role.valueOf(request.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неправильно введенный роль: " + request.role() + "!");
        }
        if (!role.name().equals("GUEST")
                && !user.getRole().name().equals("GUEST")) {
            user.setRole(role);
        }
        userRepository.save(user);
    }
}
