package com.inai.aimoh.controller;

import com.inai.aimoh.dto.paymentstatus.CreatePaymentStatusRequest;
import com.inai.aimoh.dto.paymentstatus.UpdateDescriptionOfPaymentStatusRequest;
import com.inai.aimoh.entity.PaymentStatus;
import com.inai.aimoh.service.PaymentStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-statuses")
@AllArgsConstructor
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;

    @GetMapping
    public List<PaymentStatus> getAllPaymentStatuses() {
        return paymentStatusService.findAllPaymentStatuses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPaymentStatus(@RequestBody CreatePaymentStatusRequest newPaymentStatus) {
        paymentStatusService.createPaymentStatus(newPaymentStatus);
        return ResponseEntity.ok("Успешно создан новый статус платежа!");
    }

    @PatchMapping("/edit-description-of-payment-status/{id}")
    public ResponseEntity<String> editDescriptionOfPaymentStatus(
            @PathVariable Long id,
            @RequestBody UpdateDescriptionOfPaymentStatusRequest updateDescriptionOfPaymentStatusRequest)
    {
        paymentStatusService.updateDescriptionOfPaymentStatus(id, updateDescriptionOfPaymentStatusRequest);
        return ResponseEntity.ok("Успешно обновлен описание статуса платежа!");
    }
}
