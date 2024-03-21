package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.*;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements iProjectService {

    private final ProjectRepository projectRepository;
    private final CommunityLeaderRepository communityLeaderRepository;
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final StudentResigtrationRepository studentResigtrationRepository;

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
    public Map<String, Object> getProjectById(long id) throws Exception {
        Optional<ProjectEntity> optionalProject = projectRepository.getDetailProject(id);
        Optional<CommunityLeaderEntity> optionalCommunityLeader = projectRepository.getCommunityLeaderByProjectId(id);
        if (optionalProject.isPresent()) {
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("number_of_student", studentResigtrationRepository.findAllStudentOfProject(id).get().size());
            projectMap.put("projectInformation", optionalProject.get());
            projectMap.put("leader_name", optionalCommunityLeader.get().getFullName());
            projectMap.put("leader_contact", optionalCommunityLeader.get().getPhoneNumber());
            return projectMap;
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
                    projectMap.put("maximumSchoolsRegistrationMembers", project.getMaxSchoolRegistrationMembers());
                    projectMap.put("status", project.getStatus());
                    projectMap.put("dateStart", project.getDateStart());
                    projectMap.put("dateEnd", project.getDateEnd());
                    projectMap.put("imgRoot", project.getImgRoot());
                    return projectMap;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectEntity> getAllProjectByLeaderId(long id) throws Exception {
        Optional<CommunityLeaderEntity> communityLeaderOptional = communityLeaderRepository
                .getDetailCommunityLeader(id);
        if (communityLeaderOptional.isPresent()) {
            return communityLeaderOptional.get().getProjects();
        } else {
            throw new DataNotFoundException("Cannot find projects of community leader with ID: " + id);
        }
    }

    @Override
    public List<ProjectEntity> getProjectByStudentId(long studentId) throws Exception {
        Optional<List<ProjectEntity>> projects = studentResigtrationRepository.findAllProjectOfStudent(studentId);
        if (projects.isEmpty()) {
            throw new Exception("Project of student not found");
        }
        return projects.get();
    }

    @Override
    public Map<String, Object> getProjectByLeaderIdAndProjectId(long leaderId, long projectId) throws Exception {
        if (leaderId == 0 || projectId == 0) {
            // Handle the case where leaderId or projectId is 0
            throw new DataNotFoundException(
                    "Cannot find project with leaderID haha: " + leaderId + " projectId: " + projectId);
        }

        Optional<ProjectEntity> projectOptional = studentResigtrationRepository.findAllProjectByID(projectId);
        Optional<List<StudentEntity> >studentsOptional = studentResigtrationRepository.findAllStudentOfProject(projectId);

        if (projectOptional.isPresent()) {

            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id", projectOptional.get().getId());
            projectMap.put("title", projectOptional.get().getTitle());
            projectMap.put("description", projectOptional.get().getDescription());
            projectMap.put("address", projectOptional.get().getAddress());
            projectMap.put("maximumStudents", projectOptional.get().getMaxProjectMembers());
            projectMap.put("maximumSchoolsRegistrationMembers", projectOptional.get().getMaxSchoolRegistrationMembers());
            projectMap.put("status", projectOptional.get().getStatus());
            projectMap.put("dateStart", projectOptional.get().getDateStart());
            projectMap.put("dateEnd", projectOptional.get().getDateEnd());
            projectMap.put("students", studentsOptional);
            return projectMap;
        } else {
            throw new DataNotFoundException(
                    "Cannot find project with leaderID: " + leaderId + " projectId: " + projectId + "or project not belongs to current leader");
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
    public List<ProjectEntity> getProjectByUniversityId(long universityId) throws Exception {
        if (universityId == 0) {
            // Handle the case where leaderId or projectId is 0
            throw new DataNotFoundException("Cannot find project with universityId: " + universityId);
        }
        Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(universityId);
        if (universityEntityOptional.isPresent()) {
            List<ProjectEntity> listProject = universityEntityOptional.get().getProjects();
            if(listProject.isEmpty()){
                throw new DataNotFoundException("Cannot find project with universityId: " + universityId);
            }else {
                return listProject;
            }
        } else {
            throw new DataNotFoundException("Cannot find university with universityId: " + universityId);
        }
    }

    @Override
    public void deleteProject(long id) {

    }

    @Override
    public List<StudentEntity> findAllStudentOfProject(Long projectId) throws Exception {
        Optional<List<StudentEntity>> optionalStudentsOfProject = studentResigtrationRepository.findAllStudentOfProject(projectId);
        if(optionalStudentsOfProject.isPresent()){
            return optionalStudentsOfProject.get();
        }else {
            throw new DataNotFoundException("No students found for project with ID: " + projectId);
        }
    }

    @Override
    public void rejectStudentByID(Long projectId,Long studentId) throws Exception {
        Optional<StudentsResigtrationEntity> optionalStudentsResigtration = studentResigtrationRepository.findByProjectsIdAndStudentId(projectId, studentId);
        if (optionalStudentsResigtration.isPresent()) {

            StudentsResigtrationEntity existingStudentProject = optionalStudentsResigtration.get();

            studentResigtrationRepository.delete(existingStudentProject);

        } else {
                throw new Exception("Student does not exist in the project.");
            }
    }


    @Override
    public List<StudentEntity> getAllStudentOfProjectInOrtherAddress(Long projectId,String address) throws Exception{
        Optional<List<StudentEntity>> optionalStudentsOfProject = studentResigtrationRepository.findAllStudentOfProject(projectId);
        if(optionalStudentsOfProject.isPresent()){
            // Get the list of students associated with the project
            List<StudentEntity> studentsOfProject = optionalStudentsOfProject.get();

            // Filter students based on the provided address
            List<StudentEntity> studentsWithAddress = studentRepository.findByAddressContaining(address);
            // Exclude students from studentsOfProject that are also in studentsWithAddress
            studentsOfProject.removeAll(studentsWithAddress);
            return studentsOfProject;
        }else {
            throw new DataNotFoundException("No students found for project with ID: " + projectId);
        }
    }
    @Override
    public ProjectEntity updateProjectDone(long id) throws Exception {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            ProjectEntity existingProject = optionalProject.get();
            if (existingProject.getStatus() != null)
                existingProject.setStatus("done");
            return projectRepository.save(existingProject);
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }


    @Override
    public StudentsResigtrationEntity ApproveStudent(Long studentId, Long projectId) throws Exception {
        Optional<StudentsResigtrationEntity> optionalStudentsResigtration = studentResigtrationRepository.findByProjectsIdAndStudentId(studentId, projectId);
        if (optionalStudentsResigtration.isPresent()) {
            StudentsResigtrationEntity existingStudentProject = optionalStudentsResigtration.get();
            if (existingStudentProject.getRegistration_status() != null)
                existingStudentProject.setRegistration_status("accepted");
            return studentResigtrationRepository.save(existingStudentProject);

        }
        throw new DataNotFoundException("Cannot find project with id =" + projectId);
    }
}
