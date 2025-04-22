package com.inai.aimoh.repository;

import com.inai.aimoh.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    boolean existsByName(String name);
}
