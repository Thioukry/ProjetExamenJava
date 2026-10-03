package com.sunuxam.controllers;

import com.sunuxam.dtos.DashboardDTO;
import com.sunuxam.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/admin/dashboard")
    @RequiredArgsConstructor
    public class DashboardController {

        private final DashboardService dashboardService;

        @GetMapping("/stats")
        public ResponseEntity<DashboardDTO> getStats() {
            return ResponseEntity.ok(dashboardService.getDashboardStats());
        }
    }

