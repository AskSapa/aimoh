package com.inai.aimoh.service;

import com.inai.aimoh.dto.RoomStatusDTO;
import com.inai.aimoh.entity.Room;
import com.inai.aimoh.entity.RoomStatus;
import com.inai.aimoh.repository.RoomRepository;
import com.inai.aimoh.repository.RoomStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomStatusService {

    private final RoomStatusRepository roomStatusRepository;
    private final RoomRepository roomRepository;




    /**
     * Метод для поиска всех статусов комнат.
     */

    public List<RoomStatus> findAllRoomStatuses() {
        return roomStatusRepository.findAll();
    }




    /**
     * Метод для создания статусов номеров (комнат).
     */

    @Transactional
    public void createRoomStatus(RoomStatusDTO roomStatusDTO) {
        if (roomStatusRepository.existsByName(roomStatusDTO.name())) {
            throw new IllegalArgumentException("Такой статус номера (комнаты) уже есть!");
        }
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setName(roomStatusDTO.name());
        roomStatus.setDescription(roomStatusDTO.description());
        roomStatusRepository.save(roomStatus);
    }




    /**
     * Метод для редактирования статуса комнаты (номера).
     * @param roomStatusId - это id статуса комнаты, которого хотим изменить.
     * @param roomStatusDTO - это данные, на которые хотим изменить.
     */

    @Transactional
    public void updateRoomStatus(Long roomStatusId, RoomStatusDTO roomStatusDTO) {
        RoomStatus roomStatus = roomStatusRepository.findById(roomStatusId)
                .orElseThrow(() -> new RuntimeException("Такой статус комнаты не найден!"));

        if (roomStatusRepository.existsByName(roomStatusDTO.name())
                && !(roomStatus.getName().equals(roomStatusDTO.name()))) {
            throw new IllegalArgumentException("Такой статус комнаты уже есть!");
        }

        roomStatus.setName(roomStatusDTO.name());
        roomStatus.setDescription(roomStatusDTO.description());
        roomStatusRepository.save(roomStatus);
    }




    /**
     * Метод для удаления статуса комнаты по id.
     */

    @Transactional
    public void deleteRoomStatusById(Long roomStatusId) {
        RoomStatus roomStatus = roomStatusRepository.findById(roomStatusId)
                .orElseThrow(() -> new RuntimeException("Такой статус комнаты нет, поэтому нельзя удалить!"));

        //Здесь при удалении статуса обнуляется поля тех номеров, у которых поля данного статуса
        List<Room> roomsWithThisStatus = roomRepository.findAllByRoomStatus(roomStatus);
        for (Room room : roomsWithThisStatus) {
            room.setRoomStatus(null);
        }
        roomRepository.saveAll(roomsWithThisStatus);

        roomStatusRepository.delete(roomStatus);
    }
}
