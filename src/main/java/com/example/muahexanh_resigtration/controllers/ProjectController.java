package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.responses.Project.ProjectListResponse;
import com.example.muahexanh_resigtration.responses.Project.ProjectResponse;
import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.services.Project.ProjectService;
import com.example.muahexanh_resigtration.services.Project.iProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final iProjectService projectService;

    @PostMapping("")
    public ResponseEntity<?> insertProduct(
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
            ProjectEntity productResponse = projectService.insertProject(projectDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(ProjectResponse.fromProduct(productResponse))
                    .message("Create project successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getAllProduct() throws Exception {
        List<ProjectEntity> products = projectService.getAllProject();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@Valid @PathVariable("id") Long projectId) throws Exception {
        ProjectEntity existingProduct = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(ProjectResponse.fromProduct(existingProduct))
                .message("Get detail project successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @GetMapping("/getByLeader/{id}")
    public ResponseEntity<ResponseObject> getProjectbyLeaderId(@Valid @PathVariable("id") Long leaderId) throws Exception {
        List<ProjectEntity> listProjects = projectService.getAllProjectByLeaderId(leaderId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(listProjects)
                .message("Get project successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @GetMapping("/getByLeaderIDAndProjectID/{leaderId}/{projectId}")
    public ResponseEntity<ResponseObject> getProjectbyLeaderIdAndProjectId(@Valid @PathVariable("leaderId") Long leaderId,
                                                                           @Valid @PathVariable("projectId") Long projectId) throws Exception {
        ProjectEntity project = projectService.getProjectByLeaderIdAndProjectId(leaderId,projectId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(project)
                .message("Get detail project successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(
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
}
