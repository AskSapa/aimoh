package com.inai.aimoh.dto.booking;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
        Long id,
        int roomNumber,
        String roomType,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal pricePerNight,
        BigDecimal totalPrice,
        String guestName,
        String email,
        String status
) {
}
