package com.sunuxam.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "epreuves")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class Epreuve {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nom;

        @Column(nullable = false)
        private double coefficient;

        @Column(nullable = false)
        private int dureeMinutes;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "concours_id", nullable = false)
        @JsonIgnore
        private Concours concours;





    }
