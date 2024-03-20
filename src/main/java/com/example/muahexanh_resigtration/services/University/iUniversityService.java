package com.example.muahexanh_resigtration.services.University;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.example.muahexanh_resigtration.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;

public interface iUniversityService {

    void addProjectsToUniversity(Long universityId, List<Long> projectIds) throws Exception;

    void addProjectToUniversity(Long universityId, Long projectId) throws Exception;

    UniversityEntity insertUniversity(UniversityDTO university) throws Exception;

    Optional<UniversityEntity> getUniversityById(Long id);

    List<UniversityEntity> getAllUniversities();
    List<String> getAllUniversityNameOfProject(Long projectId) throws Exception;

    UniversityEntity updateUniversity(Long id, UniversityEntity updatedUniversity);

    boolean deleteUniversity(Long id);

    UniversityEntity loginUniversity(LoginDTO loginDTO) throws Exception;
}
