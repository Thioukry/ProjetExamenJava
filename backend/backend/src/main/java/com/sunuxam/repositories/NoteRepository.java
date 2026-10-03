package com.sunuxam.repositories;
import com.sunuxam.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

    public interface NoteRepository extends JpaRepository<Note, Long> {
        List<Note> findByCandidatureId(Long candidatureId);
        Optional<Note> findByCandidatureIdAndEpreuveId(Long candidatureId, Long epreuveId);
    }

