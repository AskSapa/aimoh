package com.inai.aimoh.service;

import com.inai.aimoh.dto.paymentstatus.CreatePaymentStatusRequest;
import com.inai.aimoh.dto.paymentstatus.UpdateDescriptionOfPaymentStatusRequest;
import com.inai.aimoh.entity.PaymentStatus;
import com.inai.aimoh.repository.PaymentStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentStatusService {

    private final PaymentStatusRepository paymentStatusRepository;




    /**
     * Метод для поиска всех статусов платежей.
     */

    public List<PaymentStatus> findAllPaymentStatuses() {
        return paymentStatusRepository.findAll();
    }




    /**
     * Метод для создания статуса платежа.
     */

    @Transactional
    public void createPaymentStatus(CreatePaymentStatusRequest createPaymentStatusRequest) {
        if (paymentStatusRepository.existsByName(createPaymentStatusRequest.name())) {
            throw new IllegalArgumentException("Такой статус платежа уже есть!");
        }
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setName(createPaymentStatusRequest.name());
        paymentStatus.setDescription(createPaymentStatusRequest.description());
        paymentStatusRepository.save(paymentStatus);
    }




    /**
     * Метод для обновления описания статуса платежа по id.
     * @param paymentStatusId id обновляемого статуса.
     * @param updateDescriptionOfPaymentStatusRequest слой, где приходит новое описание для статуса.
     */

    @Transactional
    public void updateDescriptionOfPaymentStatus(
            Long paymentStatusId,
            UpdateDescriptionOfPaymentStatusRequest updateDescriptionOfPaymentStatusRequest)
    {
        PaymentStatus paymentStatus = paymentStatusRepository.findById(paymentStatusId)
                .orElseThrow(() -> new RuntimeException("Такого статуса платежа нет!"));
        paymentStatus.setDescription(updateDescriptionOfPaymentStatusRequest.description());
        paymentStatusRepository.save(paymentStatus);
    }

}
