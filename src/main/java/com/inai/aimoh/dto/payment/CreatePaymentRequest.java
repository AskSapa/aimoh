package com.inai.aimoh.dto.payment;

public record CreatePaymentRequest(
        String paymentMethod,
        Long bookingId,
        Long guestId
) {
}
