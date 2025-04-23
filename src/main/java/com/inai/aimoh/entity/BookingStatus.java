package com.inai.aimoh.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking_statuses")
@Data
public class BookingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}
