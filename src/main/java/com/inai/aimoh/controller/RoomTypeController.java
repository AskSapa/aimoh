package com.inai.aimoh.controller;

import com.inai.aimoh.dto.RoomTypeDTO;
import com.inai.aimoh.entity.RoomType;
import com.inai.aimoh.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-types")
@AllArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public List<RoomType> getAllRoomTypes() {
        return roomTypeService.findAllRoomTypes();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoomType(@RequestBody RoomTypeDTO newRoomType) {
        roomTypeService.createRoomType(newRoomType);
        return ResponseEntity.ok("Успешно создан новый тип комнаты!");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editRoomType(@PathVariable Long id, @RequestBody RoomTypeDTO roomTypeDTO) {
        roomTypeService.updateRoomType(id, roomTypeDTO);
        return ResponseEntity.ok("Успешно отредактирован тип комнаты!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomTypeById(id);
        return ResponseEntity.ok("Успешно удален данный тип комнат!");
    }
}
