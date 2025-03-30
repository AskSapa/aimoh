package com.inai.aimoh.service;

import com.inai.aimoh.dto.AdminCreateAndEditRoomTypeRateDTO;
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

    public List<RoomTypeRate> getAllRoomTypeRates() {
        return roomTypeRateRepository.findAll();
    }

    /**
     * Метод для создания тарифа для определенного типа комнат администратором.
     * @param adminCreateAndEditRoomTypeRateDTO - через этот слой передается данные
     *                                          для создания тарифа для определенного типа комнат
     */

    @Transactional
    public void createRoomTypeRate(AdminCreateAndEditRoomTypeRateDTO adminCreateAndEditRoomTypeRateDTO) {
        if (roomTypeRateRepository.existsByRoomType_Id(adminCreateAndEditRoomTypeRateDTO.roomTypeRateId())) {
            throw new IllegalArgumentException("Уже есть тариф для данного типа комнат!");
        }
        RoomType roomType = roomTypeRepository.findById(adminCreateAndEditRoomTypeRateDTO.roomTypeRateId())
                .orElseThrow(() -> new RuntimeException("Такого типа комнат нет!"));

        RoomTypeRate roomTypeRate = new RoomTypeRate();
        roomTypeRate.setPrice(adminCreateAndEditRoomTypeRateDTO.price());
        roomTypeRate.setDescription(adminCreateAndEditRoomTypeRateDTO.description());
        roomTypeRate.setRoomType(roomType);
        roomTypeRateRepository.save(roomTypeRate);
    }


}
