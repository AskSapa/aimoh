package com.inai.aimoh.controller;

import com.inai.aimoh.dto.AdminCreateRoomTypeRateDTO;
import com.inai.aimoh.dto.EditRoomTypeRateDTO;
import com.inai.aimoh.entity.RoomTypeRate;
import com.inai.aimoh.repository.RoomTypeRepository;
import com.inai.aimoh.service.RoomTypeRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-type-rates")
@AllArgsConstructor
public class RoomTypeRateController {

    private final RoomTypeRateService roomTypeRateService;
    private final RoomTypeRepository roomTypeRepository;

    @GetMapping
    public List<RoomTypeRate> getAllRoomTypeRates() {
        return roomTypeRateService.findAllRoomTypeRates();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoomTypeRate(@RequestBody AdminCreateRoomTypeRateDTO newRoomTypeRate) {
        roomTypeRateService.createRoomTypeRate(newRoomTypeRate);
        String nameOfType = roomTypeRepository.findById(newRoomTypeRate.roomTypeId()).get().getName();
        return ResponseEntity.ok("Успешно создан новый тариф для типа комнат '" + nameOfType + "'!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoomTypeRate(@PathVariable Long id) {
        String nameOfType = roomTypeRateService.findRoomTypeRateById(id).getRoomType().getName();
        roomTypeRateService.deleteRoomTypeRateById(id);
        return ResponseEntity.ok("Успешно удален данный тариф для типа комнат '" + nameOfType + "'!");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editRoomTypeRate(@PathVariable Long id, @RequestBody EditRoomTypeRateDTO editRoomTypeRateDTO) {
        roomTypeRateService.updateRoomTypeRate(id, editRoomTypeRateDTO);
        return ResponseEntity.ok("Успешно отредактирован данный тариф!");
    }
}
