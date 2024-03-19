package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
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
        ProjectEntity existingProject = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(ProjectResponse.fromProject(existingProject))
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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/university/{id}")
    public ResponseEntity<?> getProjectsOfUniversity(@PathVariable Long id) {
        try {
            List<ProjectEntity> projectResponses = projectService.getProjectByUniversityId(id);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(ProjectListResponse.fromListProject(projectResponses))
                            .message("Get all project of student successfully")
                            .status(HttpStatus.OK)
                            .build());
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
}
