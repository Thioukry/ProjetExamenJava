package com.sunuxam.entities;

import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "notes")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class Note {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Double valeur;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "epreuve_id", nullable = false)
        private Epreuve epreuve;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "candidature_id", nullable = false)
        private Candidature candidature;

}
