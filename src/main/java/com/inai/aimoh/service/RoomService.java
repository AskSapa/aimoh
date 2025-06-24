package com.inai.aimoh.service;

import com.inai.aimoh.dto.room.AdminCreateRoomDTO;
import com.inai.aimoh.dto.room.UpdateStatusOfRoomRequest;
import com.inai.aimoh.dto.room.UpdateTypeOfRoomRequest;
import com.inai.aimoh.entity.Room;
import com.inai.aimoh.entity.RoomType;
import com.inai.aimoh.entity.emun.RoomStatus;
import com.inai.aimoh.repository.RoomRepository;
import com.inai.aimoh.repository.RoomTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;




    /**
     * Метод для поиска всех номеров.
     */

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }




    /**
     * Метод для создания номера (комнаты) администратором.
     * @param request слой, где передаются необходимые данные для создания номера.
     */

    @Transactional
    public void createRoom(AdminCreateRoomDTO request) {
        if (roomRepository.existsByNumber(request.number())) {
            throw new IllegalArgumentException("Такой номер уже есть!");
        }
        Room room = new Room();
        room.setNumber(request.number());
        if (request.roomTypeId() != null) {
            RoomType roomType = roomTypeRepository.findById(request.roomTypeId())
                    .orElseThrow(() -> new RuntimeException("Для создания номера такой тип комнаты не найден!"));
            room.setRoomType(roomType);
        } else {
            room.setRoomType(null);
        }
        roomRepository.save(room);
    }




    /**
     * Метод для обновления статуса номера менеджером или администратором.
     * @param roomId это id номер, которого хотим обновить статус.
     * @param request слой, через которого передается id статуса.
     */

    @Transactional
    public void updateStatusOfRoom(Long roomId, UpdateStatusOfRoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Такого номера нет!"));
        RoomStatus roomStatus;
        try {
            roomStatus = RoomStatus.valueOf(request.roomStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неправильный статус комнаты: " + request.roomStatus() + "!");
        }
        room.setRoomStatus(roomStatus);
        roomRepository.save(room);
    }




    /**
     * Метод для обновления типа номера администратором.
     * @param roomId это id номер, которого хотим обновить тип.
     * @param request слой, через которого передается id типа.
     */

    @Transactional
    public void updateTypeOfRoom(Long roomId, UpdateTypeOfRoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Такого номера нет!"));
        Long roomTypeId = request.roomTypeId();
        if (roomTypeId != null) {
            RoomType type = roomTypeRepository.findById(roomTypeId)
                    .orElseThrow(() -> new RuntimeException("Такой тип номера не найден!"));
            room.setRoomType(type);
        } else {
            room.setRoomType(null);
        }
        roomRepository.save(room);
    }




    /**
     * Метод для удаления номера по id.
     */

    @Transactional
    public void deleteRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                        .orElseThrow(() -> new RuntimeException("Такого номера нет, поэтому нельзя удалить!"));

        roomRepository.deleteById(roomId);
    }
}
