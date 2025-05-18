package com.inai.aimoh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inai.aimoh.dto.booking.BookingResponse;
import com.inai.aimoh.dto.booking.CreateBookingRequest;
import com.inai.aimoh.service.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @MockitoBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /bookings — получение всех бронирований")
    void getAllBookings_shouldReturnListOfBookings() throws Exception {
        List<BookingResponse> bookings = List.of(
                new BookingResponse(1L, 101, "Single", LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 15), BigDecimal.valueOf(100), BigDecimal.valueOf(100), "Kirill", "kirill@gmail.com", "waiting"),
                new BookingResponse(2L, 102, "Single", LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 16), BigDecimal.valueOf(100), BigDecimal.valueOf(200), "Ivan", "ivan@gmail.com", "cancel"));

        when(bookingService.findAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        verify(bookingService, times(1)).findAllBookings();
    }

    @Test
    @DisplayName("POST /bookings/add/{guestId} — создание нового бронирования")
    void addBooking_shouldReturnSuccessMessage() throws Exception {
        Long guestId = 1L;
        CreateBookingRequest dto = new CreateBookingRequest(1L, LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 15));
        String jsonBooking = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/bookings/add/{guestId}", guestId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBooking))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно создано бронирование!"));
        verify(bookingService, times(1)).createBooking(guestId, dto);
    }

    @Test
    @DisplayName("PATCH /bookings/{bookingId}/cancel — отмена бронирования")
    void cancelBooking_shouldReturnSuccessMessage() throws Exception {
        Long bookingId = 1L;

        mockMvc.perform(patch("/bookings/{bookingId}/cancel", bookingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно отменено бронирование!"));
        verify(bookingService, times(1)).cancelBooking(bookingId);
    }
}
