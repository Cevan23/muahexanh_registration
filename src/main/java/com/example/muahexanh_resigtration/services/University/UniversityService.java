package com.example.muahexanh_resigtration.services.University;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import com.example.muahexanh_resigtration.repositories.UniversityRepository;
import com.example.muahexanh_resigtration.services.Project.iProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UniversityService implements iUniversityService {


    private final UniversityRepository universityRepository;
    private final iProjectService projectService;
    private final ProjectRepository projectRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addProjectsToUniversity(Long universityId, List<Long> projectIds) throws Exception {

        UniversityEntity university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new DataNotFoundException("University not found" + universityId);
        }

        // Check for duplicate projectIds
        Set<Long> uniqueProjectIds = new HashSet<>(projectIds);
        // Check for duplicate projectIds
        for (Long projectId : uniqueProjectIds) {
            // Check if projectId already exists in university.getProjects()
            boolean projectExists = university.getProjects().stream()
                    .anyMatch(project -> project.getId().equals(projectId));
            if (projectExists) {
                throw new DataNotFoundException("Project ID " + projectId + " already exists for this university");
            }
        }
        if (uniqueProjectIds.size() != projectIds.size()) {
            throw new DataNotFoundException("Duplicate project IDs detected");
        } else {
            // Add projects to the university
            for (Long projectId : projectIds) {
                ProjectEntity project;
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

        }
    }

    @Override
    public void addProjectToUniversity(Long universityId, Long projectId) throws Exception {

        UniversityEntity university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new DataNotFoundException("University not found" + universityId);
        }
        Optional<ProjectEntity> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new DataNotFoundException("Cannot find project with id = " + projectId);
        }
        ProjectEntity projectEntity = project.get();
        if (university.getProjects().contains(projectEntity)) {
            throw new DataNotFoundException("Project ID " + projectId + " already exists for this university");
        }
        university.getProjects().add(projectEntity);
        universityRepository.save(university);
    }

    @Override
    public UniversityEntity insertUniversity(UniversityDTO universityDTO) throws Exception {
        Optional<UniversityEntity> existingUniversity = universityRepository.findByEmail(universityDTO.getEmail());
        if (existingUniversity.isPresent()) {
            throw new Exception("Email already exists");
        }

        UniversityEntity newUniversity = UniversityEntity
                .builder()
                .email(universityDTO.getEmail())
                .fullName(universityDTO.getFullName())
                .password(passwordEncoder.encode(universityDTO.getPassword()))
                .phoneNumber(universityDTO.getPhoneNumber())
                .role(universityDTO.getRole())
                .universityName(universityDTO.getUniversityName())
                .build();

        if (newUniversity == null) {
            throw new Exception("Cannot create student");
        }

        return universityRepository.save(newUniversity);
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

    @Override
    public List<String> getAllUniversityNameOfProject(Long projectId) throws Exception {
        List<String> optionalUniversityApprovedProject = universityRepository.getAllUniversityNameOfProject(projectId);
        if (!optionalUniversityApprovedProject.isEmpty()) {
            // Create a list to store UniversityEntity objects
            List<String> universities = new ArrayList<>();

            // Iterate over the list of university names and create UniversityEntity objects
            for (String universityName : optionalUniversityApprovedProject) {
                UniversityEntity university = new UniversityEntity();
                university.setUniversityName(universityName);
                universities.add(university.getUniversityName());
            }

            return universities;
        } else {
            throw new DataNotFoundException("No universities approved for project with ID: " + projectId);
        }
    }
}
