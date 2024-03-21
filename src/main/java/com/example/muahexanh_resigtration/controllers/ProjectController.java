package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.responses.Project.ProjectListResponse;
import com.example.muahexanh_resigtration.responses.Project.ProjectResponse;
import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.services.Project.iProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final iProjectService projectService;

    @PostMapping("")
    public ResponseEntity<?> insertProject(
            @Valid @RequestBody ProjectDTO projectDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            ProjectEntity projectResponse = projectService.insertProject(projectDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(ProjectResponse.fromProject(projectResponse))
                    .message("Create project successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getAllProjects() throws Exception {
        List<Map<String, Object>> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProjectById(@Valid @PathVariable("id") Long projectId) throws Exception {
        Map<String, Object> existingProject = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(existingProject)
                .message("Get detail project successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @GetMapping("/getByLeader/{id}")
    public ResponseEntity<ResponseObject> getProjectByLeaderId(@Valid @PathVariable("id") Long leaderId) throws Exception {
        List<ProjectEntity> listProjects = projectService.getAllProjectByLeaderId(leaderId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(listProjects)
                .message("Get projects successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @GetMapping("/getProjectDetail/")
    public ResponseEntity<?> getProjectbyLeaderIdAndProjectId(@Valid @RequestParam("leaderId") Long leaderId,
                                                                           @Valid @RequestParam("projectId") Long projectId)  {
        try{
            Map<String, Object> project = projectService.getProjectByLeaderIdAndProjectId(leaderId,projectId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(project)
                    .message("Get detail project successfully")
                    .status(HttpStatus.OK)
                    .build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProject(
            @PathVariable long id,
            @RequestBody ProjectDTO projectDTO) throws Exception {
        ProjectEntity updateProject = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(updateProject)
                .message("Update project successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getProjectsOfStudent(@PathVariable Long id) {
        try {
            List<ProjectEntity> projectResponse = projectService.getProjectByStudentId(id);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(ProjectListResponse.fromListProject(projectResponse))
                            .message("Get all project of student successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(null)
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }
    }

    @GetMapping("/university/{id}")
    public ResponseEntity<?> getProjectsOfUniversity(@PathVariable Long id) {
        try {
            List<Map<String, Object>> projectResponses = projectService.getProjectByUniversityId(id);
            return new ResponseEntity<>(projectResponses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/updateDone/{id}")
    public ResponseEntity<ResponseObject> updateProjectDone(
            @PathVariable long id) throws Exception {
        ProjectEntity updateProject = projectService.updateProjectDone(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(updateProject)
                .message("Update project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/studentOfProject")
    public ResponseEntity<?> getAllStudentOfProject(@Valid @RequestParam("projectId") String projectId)
    {
        try {
            List<StudentEntity> studentOfProject = projectService.findAllStudentOfProject(Long.parseLong(projectId));
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(studentOfProject)
                    .message("Get All Student Of Project successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message("An error occurred: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @GetMapping("/studentOfProjectgetInOrtherAddress")
    public ResponseEntity<?> getAllStudentOfProjectInOrtherAddress(
            @Valid @RequestParam("projectId") String projectId,
            @Valid @RequestParam("address") String address) throws Exception  {
        try {
            List<StudentEntity> studentOfProject = projectService.getAllStudentOfProjectInOrtherAddress(Long.parseLong(projectId), address);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(studentOfProject)
                    .message("Get All Student Of Project In Orther Address successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message("An error occurred: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }
    @DeleteMapping("/rejectStudentByID/")
    public String rejectStudentByID(
            @Valid @RequestParam("studentId") Long studentId,
            @Valid @RequestParam("projectId") Long projectId)
    {
        try {
            // Call the service method to reject students by address
            projectService.rejectStudentByID(projectId, studentId);
            return "Students rejected successfully";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    @PutMapping("/approveStudent/")
    public String approveStudent(
            @Valid @RequestParam("studentId") Long studentId,
            @Valid @RequestParam("projectId") Long projectId)
    {
        try {
            projectService.ApproveStudent(studentId, projectId);
            return "Students approve successfully";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }



}
