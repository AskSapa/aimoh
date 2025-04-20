package com.inai.aimoh.repository;

import com.inai.aimoh.entity.Room;
import com.inai.aimoh.entity.RoomStatus;
import com.inai.aimoh.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNumber(int number);

    List<Room> findAllByRoomStatus(RoomStatus roomStatus);
    List<Room> findAllByRoomType(RoomType roomType);
}
