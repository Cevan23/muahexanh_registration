package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {

    Optional<UniversityEntity> findByEmail(String email);
}
