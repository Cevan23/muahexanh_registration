package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    @Query("SELECT p FROM UniversityEntity u JOIN u.projects p WHERE u.id = :universityId")
    List<ProjectEntity> getAllProjectsOfUniversity(@Param("universityId") Long universityId);

    @Query("SELECT DISTINCT u.id FROM UniversityEntity u WHERE u.universityName = :universityName")
    Long getUniversityIdFromName(@Param("universityName") String universityName);

    Optional<UniversityEntity> findByEmail(String email);
}
