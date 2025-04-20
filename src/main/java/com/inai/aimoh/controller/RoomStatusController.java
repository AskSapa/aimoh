package com.inai.aimoh.controller;

import com.inai.aimoh.dto.RoomStatusDTO;
import com.inai.aimoh.entity.RoomStatus;
import com.inai.aimoh.service.RoomStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-statuses")
@AllArgsConstructor
public class RoomStatusController {

    private final RoomStatusService roomStatusService;

    @GetMapping
    public List<RoomStatus> getAllRoomTypes() {
        return roomStatusService.findAllRoomStatuses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoomStatus(@RequestBody RoomStatusDTO newRoomStatus) {
        roomStatusService.createRoomStatus(newRoomStatus);
        return ResponseEntity.ok("Успешно создан новый статус комнаты!");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editRoomStatus(@PathVariable Long id, @RequestBody RoomStatusDTO roomStatusDTO) {
        roomStatusService.updateRoomStatus(id, roomStatusDTO);
        return ResponseEntity.ok("Успешно отредактирован тип комнаты!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoomStatus(@PathVariable Long id) {
        roomStatusService.deleteRoomStatusById(id);
        return ResponseEntity.ok("Успешно удален данный статус комнаты!");
    }
}
