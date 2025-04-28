package com.inai.aimoh.service;

import com.inai.aimoh.dto.booking.BookingResponse;
import com.inai.aimoh.dto.booking.CreateBookingRequest;
import com.inai.aimoh.entity.*;
import com.inai.aimoh.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;
    private final UserRepository userRepository;
    private final BookingStatusRepository bookingStatusRepository;



    /**
     * Метод для поиска всех бронирований.
     */

    public List<BookingResponse> findAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponse> bookingsResponse = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = new BookingResponse(
                    booking.getId(),
                    booking.getRoomNumber(),
                    booking.getRoomType(),
                    booking.getCheckIn(),
                    booking.getCheckOut(),
                    booking.getPricePerNight(),
                    booking.getTotalPrice(),
                    booking.getGuest().getFirstName() + " " + booking.getGuest().getSurname(),
                    booking.getGuest().getEmail(),
                    booking.getBookingStatus().getName()
            );
            bookingsResponse.add(bookingResponse);
        }
        return bookingsResponse;
    }




    /**
     * Метод для бронирования номера
     * @param guestId id пользователя (гостя), который хочет забронировать номер.
     * @param request слой, где приходит запрос на создание бронирования с данными.
     */

    @Transactional
    public void createBooking(Long guestId, CreateBookingRequest request) {

        // До создания бронирования идет валидация даты заезда и выезда.
        if (request.checkIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Дата заезда не может быть в прошлом!");
        }
        if (!request.checkOut().isAfter(request.checkIn())) {
            throw new IllegalArgumentException("Дата выезда должна быть позже даты заезда!");
        }

        // До создания бронирования проверяется на null значение типа номера и тарифа.
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new RuntimeException("Такого номера нет!"));
        RoomType roomType = room.getRoomType();
        if (roomType == null) {
            throw new IllegalArgumentException("Нельзя забронировать данный номер, так как не указан тип номера!");
        }


        // До создания бронирования статус номера, которого хочет забронировать, меняется на "reserved" с "available"
        RoomStatus roomStatus = room.getRoomStatus();
        if (roomStatus == null) {
            throw new IllegalArgumentException("Данный номер недоступен (статус = null)!");
        }
        if (roomStatus.getName().equals("available")) {
            room.setRoomStatus(roomStatusRepository.findByName("reserved")
                    .orElseThrow(() -> new RuntimeException("Статус 'reserved' для номеров еще не создан!")));
            roomRepository.save(room);
        } else {
           throw new IllegalArgumentException("Нельзя забронировать данный номер, так как данный номер недоступен!");
        }

        // Создается объект booking и ставится id гостя, который бронирует.
        Booking booking = new Booking();
        User guest = userRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Не найден пользователь!"));
        booking.setGuest(guest);

        // Статус бронирования принимает "ожидает оплаты".
        booking.setBookingStatus(bookingStatusRepository.findByName("waiting payment")
                .orElseThrow(() -> new RuntimeException("Статус 'waiting payment' для бронирования еще не создан!")));

        // Копируется номер комнаты и тип комнаты с room.
        booking.setRoomNumber(room.getNumber());
        booking.setRoomType(room.getRoomType().getName());

        // Устанавливаются данные
        booking.setCheckIn(request.checkIn());
        booking.setCheckOut(request.checkOut());
        RoomTypeRate roomTypeRate = roomType.getRoomTypeRate();
        if (roomTypeRate == null) {
            throw new IllegalArgumentException("Нет тарифа для данного типа номера!");
        }
        booking.setPricePerNight(roomTypeRate.getPrice());
        // Вычисление стоимости за общее количество ночей.
        int qtyOfNights = request.checkOut().getDayOfMonth() - request.checkIn().getDayOfMonth();
        BigDecimal totalPrice = booking.getPricePerNight().multiply(BigDecimal.valueOf(qtyOfNights));
        booking.setTotalPrice(totalPrice);

        bookingRepository.save(booking);
    }

}
