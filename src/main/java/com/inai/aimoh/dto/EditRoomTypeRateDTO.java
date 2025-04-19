package com.inai.aimoh.dto;

import java.math.BigDecimal;

public record EditRoomTypeRateDTO(
        BigDecimal price,
        String description
) {
}
