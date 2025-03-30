package com.inai.aimoh.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "room_type_rates")
@Data
public class RoomTypeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "room_type_id", nullable = false, unique = true)
    private RoomType roomType;
}
