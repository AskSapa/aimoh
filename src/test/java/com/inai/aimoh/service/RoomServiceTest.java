package com.inai.aimoh.service;

import com.inai.aimoh.entity.Room;
import com.inai.aimoh.entity.User;
import com.inai.aimoh.repository.RoomRepository;
import com.inai.aimoh.repository.RoomStatusRepository;
import com.inai.aimoh.repository.RoomTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomStatusRepository roomStatusRepository;
    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    @DisplayName("Список всех пользователей")
    void givenRooms_whenFindAllRooms_thenReturnRooms() {
        List<Room> rooms = List.of(new Room(), new Room(), new Room());

        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> result = roomService.findAllRooms();

        Assertions.assertEquals(3, result.size());
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();
    }
}
