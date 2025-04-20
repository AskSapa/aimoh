package com.inai.aimoh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_types")
@Data
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "roomType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private RoomTypeRate roomTypeRate;
}
