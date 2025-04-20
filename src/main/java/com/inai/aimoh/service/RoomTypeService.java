package com.inai.aimoh.service;

import com.inai.aimoh.dto.RoomTypeDTO;
import com.inai.aimoh.entity.RoomType;
import com.inai.aimoh.repository.RoomTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;




    /**
     * Метод для поиска всех типов комнат.
     */

    public List<RoomType> findAllRoomTypes() {
        return roomTypeRepository.findAll();
    }




    /**
     * Метод для создания типов номеров (комнат).
     */

    @Transactional
    public RoomType createRoomType(RoomTypeDTO roomTypeDTO) {
        if (roomTypeRepository.existsByName(roomTypeDTO.name())) {
            throw new IllegalArgumentException("Такой тип номера (комнаты) уже есть!");
        }
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeDTO.name());
        roomType.setDescription(roomTypeDTO.description());
        return roomTypeRepository.save(roomType);
    }




    /**
     * Метод для редактирования типа комнаты (номера).
     * @param roomTypeId - это id типа комнаты, которого хотим изменить.
     * @param roomTypeDTO - это данные, на которые хотим изменить.
     */

    @Transactional
    public void updateRoomType(Long roomTypeId, RoomTypeDTO roomTypeDTO) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Такой тип комнаты не найден!"));

        if (roomTypeRepository.existsByName(roomTypeDTO.name())
                && !(roomType.getName().equals(roomTypeDTO.name()))) {
            throw new IllegalArgumentException("Такой тип комнаты уже есть!");
        }

        roomType.setName(roomTypeDTO.name());
        roomType.setDescription(roomTypeDTO.description());
        roomTypeRepository.save(roomType);
    }




    /**
     * Метод для удаления типа комнаты по id.
     */

    @Transactional
    public void deleteRoomTypeById(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Такой тип комнаты нет, поэтому нельзя удалить!"));

        roomTypeRepository.delete(roomType);
    }
}
