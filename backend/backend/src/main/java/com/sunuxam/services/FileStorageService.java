package com.sunuxam.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

    @Service
    public class FileStorageService {

        private final Path fileStorageLocation;

        public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
            this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new RuntimeException("Impossible de créer le dossier de stockage des fichiers.", ex);
            }
        }

        public String storeFile(MultipartFile file) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileExtension = "";

            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String newFileName = UUID.randomUUID().toString() + fileExtension;

            try {
                if (newFileName.contains("..")) {
                    throw new RuntimeException("Le nom du fichier contient un chemin invalide : " + originalFileName);
                }

                Path targetLocation = this.fileStorageLocation.resolve(newFileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                return newFileName;
            } catch (IOException ex) {
                throw new RuntimeException("Impossible de stocker le fichier " + originalFileName, ex);
            }
        }

        public Resource loadFileAsResource(String fileName) {
            try {
                Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    throw new RuntimeException("Fichier non trouvé : " + fileName);
                }
            } catch (MalformedURLException ex) {
                throw new RuntimeException("Fichier non trouvé : " + fileName, ex);
            }
        }

}
