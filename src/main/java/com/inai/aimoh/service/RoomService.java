package com.inai.aimoh.service;

import com.inai.aimoh.dto.room.AdminCreateRoomDTO;
import com.inai.aimoh.dto.room.UpdateStatusOfRoomRequest;
import com.inai.aimoh.dto.room.UpdateTypeOfRoomRequest;
import com.inai.aimoh.entity.Room;
import com.inai.aimoh.entity.RoomStatus;
import com.inai.aimoh.entity.RoomType;
import com.inai.aimoh.repository.RoomRepository;
import com.inai.aimoh.repository.RoomStatusRepository;
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
    private final RoomStatusRepository roomStatusRepository;




    /**
     * Метод для поиска всех номеров.
     */

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }




    /**
     * Метод для создания номера (комнаты) администратором.
     * @param adminCreateRoomDTO слой, где передаются необходимые данные для создания номера.
     */

    @Transactional
    public void createRoom(AdminCreateRoomDTO adminCreateRoomDTO) {
        if (roomRepository.existsByNumber(adminCreateRoomDTO.number())) {
            throw new IllegalArgumentException("Такой номер уже есть!");
        }

        Room room = new Room();
        room.setNumber(adminCreateRoomDTO.number());

        if (adminCreateRoomDTO.roomTypeId() != null) {
            RoomType roomType = roomTypeRepository.findById(adminCreateRoomDTO.roomTypeId())
                    .orElseThrow(() -> new RuntimeException("Для создания номера такой тип комнаты не найден!"));
            room.setRoomType(roomType);
        } else {
            room.setRoomType(null);
        }

        if (adminCreateRoomDTO.roomStatusId() != null) {
            RoomStatus roomStatus = roomStatusRepository.findById(adminCreateRoomDTO.roomStatusId())
                    .orElseThrow(() -> new RuntimeException("Для создания номера такой статус комнаты не найден!"));
            room.setRoomStatus(roomStatus);
        } else {
            room.setRoomStatus(null);
        }

        roomRepository.save(room);
    }




    /**
     * Метод для обновления статуса номера менеджером или администратором.
     * @param roomId это id номер, которого хотим обновить статус.
     * @param updateStatusOfRoomRequest слой, через которого передается id статуса.
     */

    @Transactional
    public void updateStatusOfRoom(Long roomId, UpdateStatusOfRoomRequest updateStatusOfRoomRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Такого номера нет!"));

        Long roomStatusId = updateStatusOfRoomRequest.roomStatusId();
        if (roomStatusId != null) {
            RoomStatus status = roomStatusRepository.findById(roomStatusId)
                    .orElseThrow(() -> new RuntimeException("Такой статус номера не найден!"));
            room.setRoomStatus(status);
        } else {
            room.setRoomStatus(null);
        }
        roomRepository.save(room);
    }




    /**
     * Метод для обновления типа номера администратором.
     * @param roomId это id номер, которого хотим обновить тип.
     * @param updateTypeOfRoomRequest слой, через которого передается id типа.
     */

    @Transactional
    public void updateTypeOfRoom(Long roomId, UpdateTypeOfRoomRequest updateTypeOfRoomRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Такого номера нет!"));

        Long roomTypeId = updateTypeOfRoomRequest.roomTypeId();
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
