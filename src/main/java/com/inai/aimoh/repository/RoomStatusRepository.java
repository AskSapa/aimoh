package com.inai.aimoh.repository;

import com.inai.aimoh.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
    boolean existsByName(String name);

    Optional<RoomStatus> findByName(String name);
}
