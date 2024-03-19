package com.example.muahexanh_resigtration.services.University;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface iUniversityService {

    ResponseEntity<String> addProjectsToUniversity(Long universityId, List<Long> projectIds) throws Exception;

    UniversityEntity addUniversity(UniversityEntity university);

    Optional<UniversityEntity> getUniversityById(Long id);

    List<UniversityEntity> getAllUniversities();

    UniversityEntity updateUniversity(Long id, UniversityEntity updatedUniversity);

    boolean deleteUniversity(Long id);

    UniversityEntity loginUniversity(LoginDTO loginDTO) throws Exception;
}
