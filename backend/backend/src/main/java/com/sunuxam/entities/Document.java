package com.sunuxam.entities;
import com.sunuxam.enums.TypeDocument;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "documents")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class Document {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private TypeDocument type;

        @Column(nullable = false)
        private String cheminFichier;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "candidature_id", nullable = false)
        private Candidature candidature;

}
