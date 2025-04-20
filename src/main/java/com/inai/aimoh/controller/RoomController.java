package com.inai.aimoh.controller;

import com.inai.aimoh.dto.room.AdminCreateRoomDTO;
import com.inai.aimoh.dto.room.UpdateStatusOfRoomRequest;
import com.inai.aimoh.dto.room.UpdateTypeOfRoomRequest;
import com.inai.aimoh.entity.Room;
import com.inai.aimoh.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoom(@RequestBody AdminCreateRoomDTO newRoom) {
        roomService.createRoom(newRoom);
        return ResponseEntity.ok("Номер успешно создан!");
    }

    @PatchMapping("/edit-status-of-room/{id}")
    public ResponseEntity<String> editStatusOfRoom(@PathVariable Long id, @RequestBody UpdateStatusOfRoomRequest updateStatusOfRoomRequest) {
        roomService.updateStatusOfRoom(id, updateStatusOfRoomRequest);
        return ResponseEntity.ok("Статус номера успешно обновлен!");
    }

    @PatchMapping("/edit-type-of-room/{id}")
    public ResponseEntity<String> editTypeOfRoom(@PathVariable Long id, @RequestBody UpdateTypeOfRoomRequest updateTypeOfRoomRequest) {
        roomService.updateTypeOfRoom(id, updateTypeOfRoomRequest);
        return ResponseEntity.ok("Тип номера успешно обновлен!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.ok("Успешно удален данный номер!");
    }
}
