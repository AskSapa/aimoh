package com.inai.aimoh.repository;

import com.inai.aimoh.entity.RoomTypeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRateRepository extends JpaRepository<RoomTypeRate, Long> {
    boolean existsByRoomType_Id(Long roomTypeId);
}
