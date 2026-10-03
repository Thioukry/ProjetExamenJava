package com.sunuxam.controllers;
import com.sunuxam.entities.Candidature;
import com.sunuxam.enums.StatutCandidature;
import com.sunuxam.services.CandidatureService;
import com.sunuxam.services.FileStorageService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

    @RestController
    @RequestMapping("/api/candidatures")
    @RequiredArgsConstructor
    public class CandidatureController {

        private final CandidatureService candidatureService;

        @PostMapping("/postuler/{userId}/{concoursId}")
        public ResponseEntity<Candidature> postuler(@PathVariable Long userId, @PathVariable Long concoursId) {
            return ResponseEntity.ok(candidatureService.postuler(userId, concoursId));
        }

        @GetMapping("/mes-candidatures/{userId}")
        public ResponseEntity<List<Candidature>> getMesCandidatures(@PathVariable Long userId) {
            return ResponseEntity.ok(candidatureService.getMesCandidatures(userId));
        }

        @GetMapping("/admin/concours/{concoursId}")
        public ResponseEntity<List<Candidature>> getByConcours(@PathVariable Long concoursId) {
            return ResponseEntity.ok(candidatureService.getCandidaturesByConcours(concoursId));
        }

        @PutMapping("/admin/{id}/statut")
        public ResponseEntity<Candidature> changerStatut(@PathVariable Long id, @RequestParam StatutCandidature statut) {
            return ResponseEntity.ok(candidatureService.changerStatut(id, statut));
        }
        private final FileStorageService fileStorageService;

        @PostMapping("/{id}/upload")
        public ResponseEntity<Candidature> uploadDocument(
                @PathVariable Long id,
                @RequestParam("file") MultipartFile file
        ) {
            return ResponseEntity.ok(candidatureService.uploaderDocument(id, file));
        }

        @GetMapping("/download/{fileName:.+}")
        public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String contentType = null;

            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {

            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
}
