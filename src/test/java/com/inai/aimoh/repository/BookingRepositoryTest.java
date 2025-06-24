package com.inai.aimoh.repository;

import com.inai.aimoh.entity.Booking;
import com.inai.aimoh.entity.BookingStatus;
import com.inai.aimoh.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BookingStatusRepository bookingStatusRepository;

    private Booking testBooking;
    private User testUser;
    private Role testRole;
    private BookingStatus testBookingStatus;

    @BeforeEach
    public void setUp() {
        testRole = new Role();
        testRole.setName("test");
        roleRepository.save(testRole);

        testUser = new User();
        testUser.setLogin("login");
        testUser.setPassword("password");
        testUser.setEmail("email@gmail.com");
        testUser.setFirstName("Joomart");
        testUser.setLastName("Koshoibekov");
        testUser.setRole(testRole);
        testUser.setDeleted(false);
        userRepository.save(testUser);

        testBookingStatus = new BookingStatus();
        testBookingStatus.setName("test");
        bookingStatusRepository.save(testBookingStatus);

        testBooking = new Booking();
        testBooking.setRoomNumber(101);
        testBooking.setRoomType("Single");
        testBooking.setCheckIn(LocalDate.of(2025, 5, 9));
        testBooking.setCheckOut(LocalDate.of(2025, 5, 11));
        testBooking.setPricePerNight(BigDecimal.valueOf(100));
        testBooking.setTotalPrice(BigDecimal.valueOf(200));
        testBooking.setGuest(testUser);
        testBooking.setBookingStatus(testBookingStatus);
        bookingRepository.save(testBooking);
    }

    @Test
    void givenBooking_whenSaved_thenCanBeFoundById() {
        Booking savedBooking = bookingRepository.findById(testBooking.getId()).orElse(null);
        Assertions.assertNotNull(savedBooking);
        Assertions.assertEquals(testBooking.getRoomNumber(), savedBooking.getRoomNumber());
        Assertions.assertEquals(testBooking.getRoomType(), savedBooking.getRoomType());
        Assertions.assertEquals(testBooking.getCheckIn(), savedBooking.getCheckIn());
        Assertions.assertEquals(testBooking.getCheckOut(), savedBooking.getCheckOut());
        Assertions.assertEquals(testBooking.getPricePerNight(), savedBooking.getPricePerNight());
        Assertions.assertEquals(testBooking.getTotalPrice(), savedBooking.getTotalPrice());
    }

    @Test
    void givenBooking_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        testBooking.setRoomNumber(102);
        testBooking.setRoomType("King");

        Booking updatedBooking = bookingRepository.findById(testBooking.getId()).orElse(null);
        Assertions.assertNotNull(updatedBooking);
        Assertions.assertEquals(testBooking.getRoomNumber(), updatedBooking.getRoomNumber());
        Assertions.assertEquals(testBooking.getRoomType(), updatedBooking.getRoomType());
    }

    @AfterEach
    public void tearDown() {
        bookingRepository.delete(testBooking);
        userRepository.delete(testUser);
        roleRepository.delete(testRole);
        bookingStatusRepository.delete(testBookingStatus);
    }
}
