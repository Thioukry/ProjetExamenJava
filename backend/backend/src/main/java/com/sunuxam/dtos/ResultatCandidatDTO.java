package com.sunuxam.dtos;



import com.sunuxam.enums.StatutCandidature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ResultatCandidatDTO {
        private Long candidatureId;
        private String nomCandidat;
        private String prenomCandidat;
        private String emailCandidat;
        private Double moyenne;
        private StatutCandidature statut;
    }

