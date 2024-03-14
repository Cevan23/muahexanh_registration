package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("SELECT p FROM ProjectEntity p WHERE p.id = :projectId")
    Optional<ProjectEntity> getDetailProject(@Param("projectId") Long projectId);

    @Query("SELECT p FROM ProjectEntity p JOIN p.students s WHERE s.id = :leaderId")
    Optional<List<ProjectEntity>> getAllProjectByLeaderId(@Param("leaderId") Long leaderId);


}
