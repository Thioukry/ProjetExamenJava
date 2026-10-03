package com.sunuxam.entities;
import com.sunuxam.enums.StatutCandidature;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "candidatures")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class Candidature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User candidat;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "concours_id", nullable = false)
        private Concours concours;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private StatutCandidature statut;

        @Column(nullable = false)
        private LocalDateTime dateDepot;

        private Double moyenne;

        @OneToMany(mappedBy = "candidature", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Document> documents = new ArrayList<>();

        @OneToMany(mappedBy = "candidature", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Note> notes = new ArrayList<>();
        private String documentUrl;


        public void setDocumentUrl(String documentUrl) {
            this.documentUrl = documentUrl;
        }
    }
