package com.inai.aimoh.service;

import com.inai.aimoh.dto.payment.CreatePaymentRequest;
import com.inai.aimoh.dto.payment.PaymentResponse;
import com.inai.aimoh.entity.Booking;
import com.inai.aimoh.entity.Payment;
import com.inai.aimoh.entity.PaymentMethod;
import com.inai.aimoh.repository.BookingRepository;
import com.inai.aimoh.repository.BookingStatusRepository;
import com.inai.aimoh.repository.PaymentMethodRepository;
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
    private final PaymentMethodRepository paymentMethodRepository;
    private final BookingStatusRepository bookingStatusRepository;




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
                    payment.getPaymentMethod().getName(),
                    payment.getBooking().getId(),
                    payment.getBooking().getGuest().getFirstName() + " " + payment.getBooking().getGuest().getSurname()
            );
            paymentResponses.add(paymentResponse);
        }
        return paymentResponses;
    }




    /**
     * Метод, где создается данные о платеже
     * @param request
     */

    @Transactional
    public void createPayment (CreatePaymentRequest request) {
        Booking booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new RuntimeException("Нет такого бронирования!"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.paymentMethodId())
                .orElseThrow(() -> new RuntimeException("Такой тип платежа не существует"));

        if (!Objects.equals(booking.getGuest().getId(), request.guestId())) {
            throw new IllegalArgumentException("Нельзя оплатить данное бронирование, так это бронирование не ваше!");
        }

        Payment payment = new Payment();
        payment.setAmount(booking.getTotalPrice());
        payment.setPaidAt(LocalDateTime.now());
        payment.setBooking(booking);
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);

        booking.setBookingStatus(bookingStatusRepository.findByName("confirmed")
                .orElseThrow(() -> new RuntimeException("Статус 'confirmed' для бронирования еще не создан!")));
        bookingRepository.save(booking);
    }
}
