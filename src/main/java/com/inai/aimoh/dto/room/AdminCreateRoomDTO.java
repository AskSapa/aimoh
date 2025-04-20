package com.inai.aimoh.dto.room;

public record AdminCreateRoomDTO(
        int number,
        Long roomTypeId,
        Long roomStatusId
) {
}
