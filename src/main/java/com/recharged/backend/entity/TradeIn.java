package com.recharged.backend.entity;

import jakarta.persistence.*;

@Entity
public class TradeIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String vendor;
    private String condition;
    private String category;
    private String price;
}
