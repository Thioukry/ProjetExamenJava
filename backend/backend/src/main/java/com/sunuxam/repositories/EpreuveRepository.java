package com.sunuxam.repositories;
import com.sunuxam.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

    public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
        List<Epreuve> findByConcoursId(Long concoursId);
    }

