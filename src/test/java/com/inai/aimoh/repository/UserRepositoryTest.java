package com.inai.aimoh.repository;

import com.inai.aimoh.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User testUser;
    private Role testRole;

    @BeforeEach
    public void setUp() {
        testRole = new Role();
        testRole.setName("test");
        roleRepository.save(testRole);

        testUser = new User();
        testUser.setLogin("login");
        testUser.setPassword("password");
        testUser.setEmail("email@gmail.com");
        testUser.setFirstName("Joomart");
        testUser.setLastName("Koshoibekov");
        testUser.setRole(testRole);
        testUser.setDeleted(false);
        userRepository.save(testUser);
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {
        User savedUser = userRepository.findById(testUser.getId()).orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(testUser.getLogin(), savedUser.getLogin());
        Assertions.assertEquals(testUser.getPassword(), savedUser.getPassword());
        Assertions.assertEquals(testUser.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(testUser.getFirstName(), savedUser.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), savedUser.getLastName());
    }

    @Test
    void givenUser_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        testUser.setLogin("updatedLogin");
        testUser.setPassword("updatedPassword");

        User updatedUser = userRepository.findById(testUser.getId()).orElse(null);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(testUser.getLogin(), updatedUser.getLogin());
        Assertions.assertEquals(testUser.getPassword(), updatedUser.getPassword());
    }

    @Test
    void givenUser_whenExistsByLogin_thenReturnTrue() {
        Assertions.assertTrue(userRepository.existsByLogin(testUser.getLogin()));
    }

    @Test
    void givenUser_whenExistsByEmail_thenReturnTrue() {
        Assertions.assertTrue(userRepository.existsByEmail(testUser.getEmail()));
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(testUser);
        roleRepository.delete(testRole);
    }
}
