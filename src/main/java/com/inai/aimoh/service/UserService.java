package com.inai.aimoh.service;

import com.inai.aimoh.dto.AdminCreateOrEditUserDTO;
import com.inai.aimoh.entity.Role;
import com.inai.aimoh.entity.User;
import com.inai.aimoh.repository.RoleRepository;
import com.inai.aimoh.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;




    /**
     * Этот метод для создания аккаунтов сотрудников администратором.
     * Здесь админ не может создать аккаунт для гостей.
     * Совпадения login или email с login или email уже существующим пользователем не должны
     */

    @Transactional
    public void createUserByAdmin(AdminCreateOrEditUserDTO adminCreateOrEditUserDTO) {
        if (userRepository.existsByLogin(adminCreateOrEditUserDTO.login())) {
            throw new IllegalArgumentException("Логин уже занят!");
        }
        if (userRepository.existsByEmail(adminCreateOrEditUserDTO.email())) {
            throw new IllegalArgumentException("Email уже зарегистрирован!");
        }

        Role role = roleRepository.findById(adminCreateOrEditUserDTO.roleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        if ("GUEST".equals(role.getName())) {
            throw new IllegalArgumentException("Админ не может создать гостя");
        }

        User user = new User();
        user.setLogin(adminCreateOrEditUserDTO.login());
        user.setPassword(adminCreateOrEditUserDTO.password());
        user.setEmail(adminCreateOrEditUserDTO.email());
        user.setFirstName(adminCreateOrEditUserDTO.firstName());
        user.setSurname(adminCreateOrEditUserDTO.surname());
        user.setDeleted(false);
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

        if ("ADMIN".equals(user.getRole().getName())) {
            throw new IllegalArgumentException("Нельзя удалить другого администратора!");
        }

        user.setDeleted(true);
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
     * @param adminCreateOrEditUserDTO это данные, на которую хочет изменить админ.
     */

    @Transactional
    public void updateUserByAdmin(Long userId, AdminCreateOrEditUserDTO adminCreateOrEditUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        if ("ADMIN".equals(user.getRole().getName())) {
            throw new IllegalArgumentException("Нельзя изменить данные другого администратора!");
        }

        if (userRepository.existsByLogin(adminCreateOrEditUserDTO.login())
                && !(user.getLogin().equals(adminCreateOrEditUserDTO.login()))) {
            throw new IllegalArgumentException("Логин уже занят!");
        }
        if (userRepository.existsByEmail(adminCreateOrEditUserDTO.email())
                && !(user.getEmail().equals(adminCreateOrEditUserDTO.email()))) {
            throw new IllegalArgumentException("Email уже зарегистрирован!");
        }

        user.setLogin(adminCreateOrEditUserDTO.login());
        user.setPassword(adminCreateOrEditUserDTO.password());
        user.setEmail(adminCreateOrEditUserDTO.email());
        user.setFirstName(adminCreateOrEditUserDTO.firstName());
        user.setSurname(adminCreateOrEditUserDTO.surname());

        Role role = roleRepository.findById(adminCreateOrEditUserDTO.roleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        if (!role.getName().equals("GUEST")
                && !user.getRole().getName().equals("GUEST")) {
            user.setRole(role);
        }

        userRepository.save(user);
    }
}
