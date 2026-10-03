package com.sunuxam.repositories;


import com.sunuxam.entities.Concours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

    public interface ConcoursRepository extends JpaRepository<Concours, Long> {
        List<Concours> findByEstPublieTrue();
    }

