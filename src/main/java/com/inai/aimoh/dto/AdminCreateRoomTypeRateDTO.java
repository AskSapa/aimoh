package com.inai.aimoh.dto;

import java.math.BigDecimal;

public record AdminCreateRoomTypeRateDTO(
        BigDecimal price,
        String description,
        Long roomTypeId) {
}
