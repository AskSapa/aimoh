package com.inai.aimoh.repository;

import com.inai.aimoh.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
    boolean existsByName(String name);
}
