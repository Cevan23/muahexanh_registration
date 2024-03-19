package com.example.muahexanh_resigtration.services.University;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.UniversityRepository;
import com.example.muahexanh_resigtration.services.Project.iProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UniversityService implements iUniversityService{

    @Autowired
    private UniversityRepository universityRepository;
    private final iProjectService projectService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<String> addProjectsToUniversity(Long universityId, List<Long> projectIds){

        UniversityEntity university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University not found");
        }

        // Check for duplicate projectIds
        Set<Long> uniqueProjectIds = new HashSet<>(projectIds);
        // Check for duplicate projectIds
        for (Long projectId : uniqueProjectIds) {
            // Check if projectId already exists in university.getProjects()
            boolean projectExists = university.getProjects().stream()
                    .anyMatch(project -> project.getId().equals(projectId));
            if (projectExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project ID " + projectId + " already exists for this university");
            }
        }
        if (uniqueProjectIds.size() != projectIds.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate project IDs detected");
        } else {
            // Add projects to the university
            for (Long projectId : projectIds) {
                ProjectEntity project = null;
                try {
                    project = projectService.getProjectById(projectId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (project != null) {
                    university.getProjects().add(project);
                }
            }

            universityRepository.save(university);

            return ResponseEntity.ok("Projects added to university successfully");
        }
    }

    @Override
    public UniversityEntity addUniversity(UniversityEntity university) {
        return universityRepository.save(university);
    }

    @Override
    public Optional<UniversityEntity> getUniversityById(Long id) {
        return universityRepository.findById(id);
    }

    @Override
    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public UniversityEntity updateUniversity(Long id, UniversityEntity updatedUniversity) {
        UniversityEntity existingUniversity = universityRepository.findById(id).orElse(null);
        if (existingUniversity != null) {
            existingUniversity.setUniversityName(updatedUniversity.getUniversityName());
            // Cập nhật các trường khác tùy theo yêu cầu
            return universityRepository.save(existingUniversity);
        }
        return null;
    }

    @Override
    public boolean deleteUniversity(Long id) {
        UniversityEntity university = universityRepository.findById(id).orElse(null);
        if (university != null) {
            universityRepository.delete(university);
            return true;
        }
        return false;
    }

    @Override
    public UniversityEntity loginUniversity(LoginDTO loginDTO) throws Exception {
        Optional<UniversityEntity> university = universityRepository.findByEmail(loginDTO.getEmail());
        if (university.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), university.get().getPassword())) {
            throw new DataNotFoundException("Invalid email or password");
        }
        return university.get();
    }

}
