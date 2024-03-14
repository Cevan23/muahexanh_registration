package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query("SELECT s FROM StudentEntity s WHERE s.id = :studentId")
    Optional<StudentEntity> getDetailStudent(@Param("studentId") Long studentId);
}