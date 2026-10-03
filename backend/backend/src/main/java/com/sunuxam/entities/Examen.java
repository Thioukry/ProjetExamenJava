package com.sunuxam.entities;

import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "examens")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Examen {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String titre;

        @Column(columnDefinition = "TEXT")
        private String description;

        @Column(nullable = false)
        private Integer dureeMinutes;

        @Column(nullable = false)
        private String statut;
    }

