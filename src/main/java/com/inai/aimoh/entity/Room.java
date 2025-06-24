package com.inai.aimoh.entity;

import com.inai.aimoh.entity.emun.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private int number;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "room_status", nullable = false)
    private RoomStatus roomStatus = RoomStatus.AVAILABLE;

}
