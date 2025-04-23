package com.inai.aimoh.controller;

import com.inai.aimoh.dto.bookingstatus.CreateBookingStatusRequest;
import com.inai.aimoh.dto.bookingstatus.UpdateDescriptionOfBookingStatusRequest;
import com.inai.aimoh.entity.BookingStatus;
import com.inai.aimoh.service.BookingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-statuses")
@AllArgsConstructor
public class BookingStatusController {

    private final BookingStatusService bookingStatusService;

    @GetMapping
    public List<BookingStatus> getAllBookingStatuses() {
        return bookingStatusService.findAllBookingStatuses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookingStatus(@RequestBody CreateBookingStatusRequest newBookingStatus) {
        bookingStatusService.createBookingStatus(newBookingStatus);
        return ResponseEntity.ok("Успешно создан новый статус бронирования!");
    }

    @PatchMapping("/edit-description-of-booking-status/{id}")
    public ResponseEntity<String> editDescriptionOfBookingStatus(
            @PathVariable Long id,
            @RequestBody UpdateDescriptionOfBookingStatusRequest updateDescriptionOfBookingStatusRequest)
    {
        bookingStatusService.updateDescriptionOfBookingStatus(id, updateDescriptionOfBookingStatusRequest);
        return ResponseEntity.ok("Успешно обновлен описание статуса бронирования!");
    }
}
