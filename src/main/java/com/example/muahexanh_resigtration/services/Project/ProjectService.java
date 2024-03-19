package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements iProjectService {

    private final ProjectRepository projectRepository;
    private final CommunityLeaderRepository communityLeaderRepository;

    @Override
    public ProjectEntity insertProject(ProjectDTO projectDTO) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

        java.sql.Date sqlDateStart, sqlDateEnd;

        java.util.Date parsed = format.parse(projectDTO.getDateStart());
        sqlDateStart = new java.sql.Date(parsed.getTime());

        parsed = format.parse(projectDTO.getDateEnd());
        sqlDateEnd = new java.sql.Date(parsed.getTime());

        ProjectEntity newProject = ProjectEntity
                .builder()
                .title(projectDTO.getTitle())
                .address(projectDTO.getAddress())
                .description(projectDTO.getDescription())
                .status(projectDTO.getStatus())
                .dateStart(sqlDateStart)
                .dateEnd(sqlDateEnd)
                .imgRoot(projectDTO.getImgRoot())
                .maxProjectMembers(projectDTO.getMaxProjectMembers())
                .maxSchoolRegistrationMembers(projectDTO.getMaxSchoolRegistrationMembers())
                .build();
        return projectRepository.save(newProject);
    }

    @Override
    public ProjectEntity getProjectById(long id) throws Exception {
        Optional<ProjectEntity> optionalProduct = projectRepository.getDetailProject(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }

    @Override
    public List<Map<String, Object>> getAllProjects() {
        List<ProjectEntity> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> {
                    Map<String, Object> projectMap = new HashMap<>();
                    projectMap.put("id", project.getId());
                    projectMap.put("title", project.getTitle());
                    projectMap.put("description", project.getDescription());
                    projectMap.put("address", project.getAddress());
                    projectMap.put("maximumStudents", project.getMaxProjectMembers());
                    projectMap.put("maximumSchools", project.getMaxSchoolRegistrationMembers());
                    projectMap.put("status", project.getStatus());
                    projectMap.put("dateStart", project.getDateStart());
                    projectMap.put("dateEnd", project.getDateEnd());
                    projectMap.put("imgRoot", project.getImgRoot());
                    projectMap.put("students", project.getStudents().stream()
                            .map(StudentEntity::getId)
                            .collect(Collectors.toList()));
                    return projectMap;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectEntity> getAllProjectByLeaderId(long id) throws Exception {
        Optional<CommunityLeaderEntity> communityLeaderOptional = communityLeaderRepository.getDetailCommunityLeader(id);
        if (communityLeaderOptional.isPresent()) {
            return communityLeaderOptional.get().getProjects();
        } else {
            throw new DataNotFoundException("Cannot find projects of community leader with ID: " + id);
        }
    }

    @Override
    public List<ProjectEntity> getProjectByStudentId(long studentId) throws Exception {
        Optional<List<ProjectEntity>> projects = projectRepository.findByStudentId(studentId);
        if (projects.isEmpty()) {
            throw new Exception("Project of student not found");
        }
        return projects.get();
    }

    @Override
    public Map<String, Object> getProjectByLeaderIdAndProjectId(long leaderId, long projectId) throws Exception {
        if (leaderId == 0 || projectId == 0) {
            // Handle the case where leaderId or projectId is 0
            throw new DataNotFoundException("Cannot find project with leaderID: " + leaderId + " projectId: " + projectId);
        }
        Optional<CommunityLeaderEntity> communityLeaderOptional = communityLeaderRepository.getDetailCommunityLeader(leaderId);
        Optional<ProjectEntity> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {

            List<Map<String, Object>> studentsInfo = projectOptional.get().getStudents().stream()
                    .map(student -> {
                        Map<String, Object> studentMap = new HashMap<>();
                        studentMap.put("id", student.getId());
                        studentMap.put("address", student.getAddress());
                        studentMap.put("phone_number", student.getPhoneNumber());
                        studentMap.put("full_name", student.getFullName());
                        return studentMap;
                    })
                    .toList();

            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id", projectOptional.get().getId());
            projectMap.put("title", projectOptional.get().getTitle());
            projectMap.put("description", projectOptional.get().getDescription());
            projectMap.put("address", projectOptional.get().getAddress());
            projectMap.put("maximumStudents", projectOptional.get().getMaxProjectMembers());
            projectMap.put("maximumSchools", projectOptional.get().getMaxSchoolRegistrationMembers());
            projectMap.put("status", projectOptional.get().getStatus());
            projectMap.put("dateStart", projectOptional.get().getDateStart());
            projectMap.put("dateEnd", projectOptional.get().getDateEnd());
            projectMap.put("students", studentsInfo);
            return projectMap;
        } else {
            throw new DataNotFoundException("Cannot find project with leaderID: " + leaderId + " projectId: " + projectId);
        }
    }

    @Override
    public ProjectEntity updateProject(long id, ProjectDTO projectDTO) throws Exception {
        Optional<ProjectEntity> optionalProduct = projectRepository.findById(id);
        if (optionalProduct.isPresent()) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            java.util.Date parsed;
            java.sql.Date sqlDateStart, sqlDateEnd;

            ProjectEntity existingProject = optionalProduct.get();

            if (projectDTO.getTitle() != null)
                existingProject.setTitle(projectDTO.getTitle());
            if (projectDTO.getDescription() != null)
                existingProject.setDescription(projectDTO.getDescription());
            if (projectDTO.getStatus() != null)
                existingProject.setStatus(projectDTO.getStatus());
            if (projectDTO.getImgRoot() != null)
                existingProject.setImgRoot(projectDTO.getImgRoot());
            if (projectDTO.getAddress() != null)
                existingProject.setAddress(projectDTO.getAddress());
            if (projectDTO.getMaxSchoolRegistrationMembers() != 0)
                existingProject.setMaxSchoolRegistrationMembers(projectDTO.getMaxSchoolRegistrationMembers());
            if (projectDTO.getMaxProjectMembers() != 0)
                existingProject.setMaxProjectMembers(projectDTO.getMaxProjectMembers());
            if (projectDTO.getDateStart() != null) {
                parsed = format.parse(projectDTO.getDateStart());
                sqlDateStart = new java.sql.Date(parsed.getTime());
                existingProject.setDateStart(sqlDateStart);
            }
            if (projectDTO.getDateEnd() != null) {
                parsed = format.parse(projectDTO.getDateEnd());
                sqlDateEnd = new java.sql.Date(parsed.getTime());
                existingProject.setDateEnd(sqlDateEnd);
            }
            return projectRepository.save(existingProject);
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }

    @Override
    public void deleteProject(long id) {

    }
}
