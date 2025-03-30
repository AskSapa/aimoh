package com.inai.aimoh.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_statuses")
@Data
public class RoomStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "dedescription")
    private String description;
}
