package com.inai.aimoh.dto.payment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        BigDecimal amount,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
        LocalDateTime paidAt,
        String paymentMethod,
        Long bookingId,
        String guestName
) {
}
