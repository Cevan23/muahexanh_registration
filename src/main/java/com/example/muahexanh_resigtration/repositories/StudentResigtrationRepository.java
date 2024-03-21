package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.StudentsResigtrationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResigtrationRepository extends JpaRepository<StudentsResigtrationEntity,StudentsResigtrationEntityId> {

    @Query("SELECT sr.student FROM StudentsResigtrationEntity sr WHERE sr.project.id = :projectId")
    Optional< List<StudentEntity> >  findAllStudentOfProject(@Param("projectId") Long projectId);



    @Query("SELECT sr FROM StudentsResigtrationEntity sr WHERE sr.id.projectId = :projectId and sr.id.studentId = :studentId")
    Optional<StudentsResigtrationEntity> findByProjectsIdAndStudentId(@Param("projectId") Long projectId, @Param("studentId") Long studentId);

    @Query("SELECT sr.project FROM StudentsResigtrationEntity sr WHERE sr.student.id = :studentId")
    Optional<List<ProjectEntity> >  findAllProjectOfStudent(@Param("studentId") Long studentId);

    @Query("SELECT sr.project FROM StudentsResigtrationEntity sr WHERE sr.project.id = :projectId")
    Optional<ProjectEntity>  findAllProjectByID(@Param("projectId") Long projectId);

    @Transactional
    @Modifying
    @Query("DELETE FROM StudentsResigtrationEntity sr WHERE sr.id.studentId = :studentId AND sr.id.projectId = :projectId")
    void deleteByStudentIdAndProjectId(Long studentId, Long projectId);
}
