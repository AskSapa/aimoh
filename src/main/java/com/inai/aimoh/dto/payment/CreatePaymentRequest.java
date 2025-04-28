package com.inai.aimoh.dto.payment;

public record CreatePaymentRequest(
        Long bookingId,
        Long guestId,
        Long paymentMethodId
) {
}
