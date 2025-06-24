package com.inai.aimoh.service;

import com.inai.aimoh.dto.AdminCreateOrEditUserDTO;
import com.inai.aimoh.entity.User;
import com.inai.aimoh.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Успешное создание пользователя")
    void givenValidData_whenCreateUserByAdmin_thenUserIsSaved() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "askat",
                "1234",
                "askatsaparov@gmail.com",
                "Askat",
                "Saparov",
                1L
        );

        Mockito.when(userRepository.existsByLogin("askat")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("askatsaparov@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        userService.createUserByAdmin(dto);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Логин уже занят")
    void givenTakenLogin_whenCreateUserByAdmin_thenThrowException() {

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "askat",
                "1234",
                "askatsaparov@gmail.com",
                "Askat",
                "Saparov",
                1L
        );

        Mockito.when(userRepository.existsByLogin("askat")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUserByAdmin(dto));
    }

    @Test
    @DisplayName("Email уже занят")
    void givenTakenEmail_whenCreateUserByAdmin_thenThrowException() {

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "askat",
                "1234",
                "askatsaparov@gmail.com",
                "Askat",
                "Saparov",
                1L
        );

        Mockito.when(userRepository.existsByLogin("askat")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("askatsaparov@gmail.com")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUserByAdmin(dto));
    }

    @Test
    @DisplayName("Роль не найдена")
    void givenInvalidRoleId_whenCreateUserByAdmin_thenThrowException() {

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "askat",
                "1234",
                "askatsaparov@gmail.com",
                "Askat",
                "Saparov",
                99L
        );

        Mockito.when(userRepository.existsByLogin("askat")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("askatsaparov@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> userService.createUserByAdmin(dto));
    }

    @Test
    @DisplayName("Попытка создать гостя")
    void givenGuestRole_whenCreateUserByAdmin_thenThrowException() {
        Role role = new Role();
        role.setId(1L);
        role.setName("GUEST");

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "askat",
                "1234",
                "askatsaparov@gmail.com",
                "Askat",
                "Saparov",
                1L
        );

        Mockito.when(userRepository.existsByLogin("askat")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("askatsaparov@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUserByAdmin(dto));
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void givenValidData_whenDeleteUserByAdmin_thenUserIsDeleted() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        Mockito.when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        userService.deleteUserByAdmin(testUser.getId());

        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
    }

    @Test
    @DisplayName("Не найден пользователь")
    void givenNothing_whenDeleteUserByAdmin_thenThrowException() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> userService.deleteUserByAdmin(1L));
    }

    @Test
    @DisplayName("Попытка удалить другого админа")
    void givenAdminRole_whenDeleteUserByAdmin_thenThrowException() {

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        Mockito.when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByAdmin(testUser.getId()));
    }

    @Test
    @DisplayName("Список всех пользователей")
    void givenUsers_whenFindAllUsers_thenReturnUsers() {
        List<User> users = List.of(new User(), new User(), new User());

        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.findAllUsers();

        Assertions.assertEquals(3, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Успешное обновление пользователя")
    void whenUserExistsAndValid_thenUpdateSuccess() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        userService.updateUserByAdmin(1L, dto);

        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        Assertions.assertEquals("newLogin", testUser.getLogin());
        Assertions.assertEquals("newPassword", testUser.getPassword());
        Assertions.assertEquals("new@gmail.com", testUser.getEmail());
        Assertions.assertEquals("Ivan", testUser.getFirstName());
        Assertions.assertEquals("Ivanov", testUser.getLastName());
    }

    @Test
    @DisplayName("Пользователь не найден для обновления")
    void whenUserNotFound_thenThrowException() {

        var dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> userService.updateUserByAdmin(1L, dto));
    }

    @Test
    @DisplayName("Попытка изменить администратора")
    void whenUserIsAdmin_thenThrowException() {

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.updateUserByAdmin(1L, dto));
    }

    @Test
    @DisplayName("Логин уже занят другим пользователем")
    void whenLoginTakenByAnother_thenThrowException() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.updateUserByAdmin(1L, dto));
    }

    @Test
    @DisplayName("Email уже зарегистрирован другим пользователем")
    void whenEmailTakenByAnother_thenThrowException() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@gmail.com")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.updateUserByAdmin(1L, dto));
    }

    @Test
    @DisplayName("Роль не найдена на которую меняется")
    void whenRoleNotFound_thenThrowException() {

        Role role = new Role();
        role.setId(1L);
        role.setName("MANAGER");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(role);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                99L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> userService.updateUserByAdmin(1L, dto));
    }

    @Test
    @DisplayName("Новая роль — GUEST, но текущая — не GUEST")
    void whenNewRoleIsGuestAndCurrentNotGuest_thenRoleNotUpdated() {

        Role currentRole = new Role();
        currentRole.setId(1L);
        currentRole.setName("MANAGER");

        Role guestRole = new Role();
        guestRole.setId(2L);
        guestRole.setName("GUEST");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(currentRole);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                2L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(2L)).thenReturn(Optional.of(guestRole));

        userService.updateUserByAdmin(1L, dto);

        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        Assertions.assertEquals("newLogin", testUser.getLogin());
        Assertions.assertEquals("newPassword", testUser.getPassword());
        Assertions.assertEquals("new@gmail.com", testUser.getEmail());
        Assertions.assertEquals("Ivan", testUser.getFirstName());
        Assertions.assertEquals("Ivanov", testUser.getLastName());
        Assertions.assertEquals(1L, testUser.getRole().getId());
    }

    @Test
    @DisplayName("Обновление роли, если обе роли ≠ GUEST")
    void whenRolesAreNotGuest_thenRoleUpdated() {

        Role currentRole = new Role();
        currentRole.setId(1L);
        currentRole.setName("MANAGER");

        Role newRole = new Role();
        newRole.setId(2L);
        newRole.setName("RES");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("askat");
        testUser.setPassword("1234");
        testUser.setEmail("askatsaparov@gmail.com");
        testUser.setFirstName("Askat");
        testUser.setLastName("Saparov");
        testUser.setRole(currentRole);
        testUser.setDeleted(false);

        AdminCreateOrEditUserDTO dto = new AdminCreateOrEditUserDTO(
                "newLogin",
                "newPassword",
                "new@gmail.com",
                "Ivan",
                "Ivanov",
                2L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.existsByLogin("newLogin")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        Mockito.when(roleRepository.findById(2L)).thenReturn(Optional.of(newRole));

        userService.updateUserByAdmin(1L, dto);

        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        Assertions.assertEquals("newLogin", testUser.getLogin());
        Assertions.assertEquals("newPassword", testUser.getPassword());
        Assertions.assertEquals("new@gmail.com", testUser.getEmail());
        Assertions.assertEquals("Ivan", testUser.getFirstName());
        Assertions.assertEquals("Ivanov", testUser.getLastName());
        Assertions.assertEquals(2L, testUser.getRole().getId());
    }
}
