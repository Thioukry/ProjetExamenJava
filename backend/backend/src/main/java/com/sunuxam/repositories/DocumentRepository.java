package com.sunuxam.repositories;
import com.sunuxam.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

    public interface DocumentRepository extends JpaRepository<Document, Long> {
        List<Document> findByCandidatureId(Long candidatureId);
    }

