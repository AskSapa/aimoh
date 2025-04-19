package com.inai.aimoh.service;

import com.inai.aimoh.dto.AdminCreateRoomTypeRateDTO;
import com.inai.aimoh.dto.EditRoomTypeRateDTO;
import com.inai.aimoh.entity.RoomType;
import com.inai.aimoh.entity.RoomTypeRate;
import com.inai.aimoh.repository.RoomTypeRateRepository;
import com.inai.aimoh.repository.RoomTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomTypeRateService {

    private final RoomTypeRateRepository roomTypeRateRepository;
    private final RoomTypeRepository roomTypeRepository;




    /**
     * Метод для поиска всех тарифов для типов комнат.
     * Нужно учитывать, что один тариф только для одного типа комнаты.
     */

    public List<RoomTypeRate> findAllRoomTypeRates() {
        return roomTypeRateRepository.findAll();
    }




    /**
     * Метод для создания тарифа для определенного типа комнат администратором.
     * @param adminCreateRoomTypeRateDTO - через этот слой передается данные
     *                                          для создания тарифа для определенного типа комнат
     */

    @Transactional
    public void createRoomTypeRate(AdminCreateRoomTypeRateDTO adminCreateRoomTypeRateDTO) {
        if (roomTypeRateRepository.existsByRoomType_Id(adminCreateRoomTypeRateDTO.roomTypeId())) {
            throw new IllegalArgumentException("Уже есть тариф для данного типа комнат!");
        }
        RoomType roomType = roomTypeRepository.findById(adminCreateRoomTypeRateDTO.roomTypeId())
                .orElseThrow(() -> new RuntimeException("Такого типа комнат нет!"));

        RoomTypeRate roomTypeRate = new RoomTypeRate();
        roomTypeRate.setPrice(adminCreateRoomTypeRateDTO.price());
        roomTypeRate.setDescription(adminCreateRoomTypeRateDTO.description());
        roomTypeRate.setRoomType(roomType);
        roomTypeRateRepository.save(roomTypeRate);
    }




    /**
     * Метод для удаления тарифа для определенного типа комнат администратором.
     */

    @Transactional
    public void deleteRoomTypeRateById(Long roomTypeRateId) {
        roomTypeRateRepository.deleteById(roomTypeRateId);
    }




    /**
     * Метод для поиска тарифа определенного типа комнат по id.
     */

    public RoomTypeRate findRoomTypeRateById(Long roomTypeRateId) {
        return roomTypeRateRepository.findById(roomTypeRateId)
                .orElseThrow(() -> new RuntimeException("Такого тарифа нет!"));
    }

    /**
     * Метод для редактирования тарифа либо администратором, либо менеджером. Редактировать могут только
     * цену и описание (price, description).
     * @param roomTypeRateId редактируется через id сущности RoomTypeRate.
     * @param editRoomTypeRateDTO через этот слой передается данные цены и описания.
     */

    @Transactional
    public void updateRoomTypeRate(Long roomTypeRateId, EditRoomTypeRateDTO editRoomTypeRateDTO) {
        RoomTypeRate roomTypeRate = roomTypeRateRepository.findById(roomTypeRateId)
                .orElseThrow(() -> new RuntimeException("Такого тарифа нет!"));

        roomTypeRate.setPrice(editRoomTypeRateDTO.price());
        roomTypeRate.setDescription(editRoomTypeRateDTO.description());
        roomTypeRateRepository.save(roomTypeRate);
    }
}
