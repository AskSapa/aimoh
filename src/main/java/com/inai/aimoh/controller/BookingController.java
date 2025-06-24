package com.inai.aimoh.controller;

import com.inai.aimoh.dto.booking.BookingResponse;
import com.inai.aimoh.dto.booking.CreateBookingRequest;
import com.inai.aimoh.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.findAllBookings();
    }

    @PostMapping("/add/{guestId}")
    public ResponseEntity<String> addBooking(@PathVariable Long guestId, @RequestBody CreateBookingRequest newBooking) {
        bookingService.createBooking(guestId, newBooking);
        return ResponseEntity.ok("Успешно создано бронирование!");
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Успешно отменено бронирование!");
    }
}
