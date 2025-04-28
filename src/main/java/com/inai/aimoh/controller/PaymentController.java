package com.inai.aimoh.controller;

import com.inai.aimoh.dto.payment.CreatePaymentRequest;
import com.inai.aimoh.dto.payment.PaymentResponse;
import com.inai.aimoh.entity.Payment;
import com.inai.aimoh.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.findAllPayments();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
        paymentService.createPayment(createPaymentRequest);
        return ResponseEntity.ok("Успешно прошла оплата!");
    }
}
