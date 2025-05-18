package com.inai.aimoh.repository;

import com.inai.aimoh.entity.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    private Room testRoom;

    @BeforeEach
    public void setUp() {
        testRoom = new Room();
        testRoom.setNumber(101);
        roomRepository.save(testRoom);
    }

    @Test
    void givenRoom_whenSaved_thenCanBeFoundById() {
        Room savedRoom = roomRepository.findById(testRoom.getId()).orElse(null);
        Assertions.assertNotNull(savedRoom);
        Assertions.assertEquals(testRoom.getNumber(), savedRoom.getNumber());
    }

    @Test
    void givenRoom_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        testRoom.setNumber(102);

        Room updatedRoom = roomRepository.findById(testRoom.getId()).orElse(null);
        Assertions.assertNotNull(updatedRoom);
        Assertions.assertEquals(testRoom.getNumber(), updatedRoom.getNumber());
    }

    @Test
    void givenRoom_whenExistsByNumber_thenReturnTrue() {
        Assertions.assertTrue(roomRepository.existsByNumber(testRoom.getNumber()));
    }

    @AfterEach
    public void tearDown() {
        roomRepository.delete(testRoom);
    }
}
