package com.sunuxam.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class DashboardDTO {
        private long totalCandidats;
        private long totalConcours;
        private long totalCandidatures;
        private long candidaturesEnAttente;
        private long candidaturesAcceptees;
        private long candidaturesRejetees;
    }

