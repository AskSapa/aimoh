package com.inai.aimoh.service;

import com.inai.aimoh.dto.payment.CreatePaymentRequest;
import com.inai.aimoh.dto.payment.PaymentResponse;
import com.inai.aimoh.entity.Booking;
import com.inai.aimoh.entity.Payment;
import com.inai.aimoh.entity.emun.BookingStatus;
import com.inai.aimoh.entity.emun.PaymentMethod;
import com.inai.aimoh.repository.BookingRepository;
import com.inai.aimoh.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;




    /**
     * Метод для поиска всех платежей.
     */

    public List<PaymentResponse> findAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment: payments) {
            PaymentResponse paymentResponse = new PaymentResponse(
                    payment.getId(),
                    payment.getAmount(),
                    payment.getPaidAt(),
                    payment.getPaymentMethod().name(),
                    payment.getBooking().getId(),
                    payment.getBooking().getGuest().getFirstName() + " " + payment.getBooking().getGuest().getLastName()
            );
            paymentResponses.add(paymentResponse);
        }
        return paymentResponses;
    }




    /**
     * Метод, где создается данные о платеже
     */

    @Transactional
    public void createPayment (CreatePaymentRequest request) {
        Booking booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new RuntimeException("Нет такого бронирования!"));
        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(request.paymentMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Такой тип платежа не существует: " + request.paymentMethod() + "!");
        }
        if (!Objects.equals(booking.getGuest().getId(), request.guestId())) {
            throw new IllegalArgumentException("Нельзя оплатить данное бронирование, так это бронирование не ваше!");
        }
        Payment payment = new Payment();
        payment.setAmount(booking.getTotalPrice());
        payment.setPaidAt(LocalDateTime.now());
        payment.setBooking(booking);
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }
}
