package com.inai.aimoh.service;

import com.inai.aimoh.dto.bookingstatus.CreateBookingStatusRequest;
import com.inai.aimoh.dto.bookingstatus.UpdateDescriptionOfBookingStatusRequest;
import com.inai.aimoh.entity.BookingStatus;
import com.inai.aimoh.repository.BookingStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingStatusService {

    private final BookingStatusRepository bookingStatusRepository;




    /**
     * Метод для поиска всех статусов бронирований.
     */

    public List<BookingStatus> findAllBookingStatuses() {
        return bookingStatusRepository.findAll();
    }




    /**
     * Метод для создания статуса бронирования.
     */

    @Transactional
    public void createBookingStatus(CreateBookingStatusRequest createBookingStatusRequest) {
        if (bookingStatusRepository.existsByName(createBookingStatusRequest.name())) {
            throw new IllegalArgumentException("Такой статус бронирования уже есть!");
        }
        BookingStatus bookingStatus = new BookingStatus();
        bookingStatus.setName(createBookingStatusRequest.name());
        bookingStatus.setDescription(createBookingStatusRequest.description());
        bookingStatusRepository.save(bookingStatus);
    }




    /**
     * Метод для обновления описания статуса бронирования по id.
     * @param bookingStatusId id обновляемого статуса.
     * @param updateDescriptionOfBookingStatusRequest слой, где приходит новое описание для статуса.
     */

    @Transactional
    public void updateDescriptionOfBookingStatus(
            Long bookingStatusId,
            UpdateDescriptionOfBookingStatusRequest updateDescriptionOfBookingStatusRequest)
    {
        BookingStatus bookingStatus = bookingStatusRepository.findById(bookingStatusId)
                .orElseThrow(() -> new RuntimeException("Такого статуса бронирования нет!"));
        bookingStatus.setDescription(updateDescriptionOfBookingStatusRequest.description());
        bookingStatusRepository.save(bookingStatus);
    }
}
