package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    @Query("SELECT DISTINCT u.universityName FROM UniversityEntity u JOIN u.projects p WHERE p.id = :projectId")
    List<String> getAllUniversityNameOfProject(@Param("projectId") Long projectId);

    Optional<UniversityEntity> findByEmail(String email);
}
