package com.inai.aimoh.controller;

import com.inai.aimoh.dto.PaymentMethodRequest;
import com.inai.aimoh.entity.PaymentMethod;
import com.inai.aimoh.service.PaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
@AllArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.findAllPaymentMethods();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethodRequest newPaymentMethod) {
        paymentMethodService.createPaymentMethod(newPaymentMethod);
        return ResponseEntity.ok("Успешно создан новый метод платежа!");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editPaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodRequest paymentMethodRequest) {
        paymentMethodService.updatePaymentMethod(id, paymentMethodRequest);
        return ResponseEntity.ok("Успешно отредактирован метод платежа!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethodById(id);
        return ResponseEntity.ok("Успешно удален данный метод платежа!");
    }
}
