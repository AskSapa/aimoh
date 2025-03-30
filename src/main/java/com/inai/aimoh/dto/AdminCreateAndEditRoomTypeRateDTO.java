package com.inai.aimoh.dto;

import java.math.BigDecimal;

public record AdminCreateAndEditRoomTypeRateDTO(
        BigDecimal price,
        String description,
        Long roomTypeRateId) {
}
