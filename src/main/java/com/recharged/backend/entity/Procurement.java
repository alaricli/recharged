package com.recharged.backend.entity;

import jakarta.persistence.*;

@Entity
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}
