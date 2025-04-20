package com.inai.aimoh.dto.roomtyperate;

import java.math.BigDecimal;

public record EditRoomTypeRateDTO(
        BigDecimal price,
        String description
) {
}
