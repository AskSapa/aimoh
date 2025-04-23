package com.inai.aimoh.repository;

import com.inai.aimoh.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {
    boolean existsByName(String name);
}
