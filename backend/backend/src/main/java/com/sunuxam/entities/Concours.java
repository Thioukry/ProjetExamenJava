package com.sunuxam.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "concours")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class Concours {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String titre;

        @Column(length = 2000)
        private String description;

        @Column(nullable = false)
        private LocalDate dateLimite;

        @Column(nullable = false)
        private LocalDate dateDeliberation;

        @Column(nullable = false)
        private boolean estPublie = false;

        @OneToMany(mappedBy = "concours", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Epreuve> epreuves = new ArrayList<>();


        public boolean getEstPublie() {
            return estPublie;
        }
    }
