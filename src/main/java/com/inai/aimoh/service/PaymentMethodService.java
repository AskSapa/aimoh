package com.inai.aimoh.service;

import com.inai.aimoh.dto.PaymentMethodRequest;
import com.inai.aimoh.entity.PaymentMethod;
import com.inai.aimoh.repository.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;




    /**
     * Метод для поиска всех методов платежей.
     */

    public List<PaymentMethod> findAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }




    /**
     * Метод для создания метода платежа.
     */

    @Transactional
    public PaymentMethod createPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        if (paymentMethodRepository.existsByName(paymentMethodRequest.name())) {
            throw new IllegalArgumentException("Такой метод платежа уже есть!");
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(paymentMethodRequest.name());
        paymentMethod.setDescription(paymentMethodRequest.description());
        return paymentMethodRepository.save(paymentMethod);
    }




    /**
     * Метод для редактирования метода платежа.
     * @param paymentMethodId - это id типа комнаты, которого хотим изменить.
     * @param paymentMethodRequest - это данные, на которые хотим изменить.
     */

    @Transactional
    public void updatePaymentMethod(Long paymentMethodId, PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Такой метод платежа не найден!"));

        if (paymentMethodRepository.existsByName(paymentMethodRequest.name())
                && !(paymentMethod.getName().equals(paymentMethodRequest.name()))) {
            throw new IllegalArgumentException("Такой метод платежа уже есть!");
        }

        paymentMethod.setName(paymentMethodRequest.name());
        paymentMethod.setDescription(paymentMethodRequest.description());
        paymentMethodRepository.save(paymentMethod);
    }




    /**
     * Метод для удаления метода платежа по id.
     */

    @Transactional
    public void deletePaymentMethodById(Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Такого метода платежа нет, поэтому нельзя удалить!"));
        paymentMethodRepository.delete(paymentMethod);
    }
}
