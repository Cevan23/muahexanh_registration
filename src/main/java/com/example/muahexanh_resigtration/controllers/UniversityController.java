package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.example.muahexanh_resigtration.dtos.UniversityProjectRequest;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;

import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.responses.Student.StudentResponse;
import com.example.muahexanh_resigtration.responses.University.UniversityResponse;
import com.example.muahexanh_resigtration.services.University.iUniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
public class UniversityController {
    private final iUniversityService universityService;
    @PostMapping("/universities/projects")
    public String addProjectsToUniversity(@Valid @RequestBody Map<String, List<String> > requestBody) throws Exception {
        String universityId = requestBody.get("universityId").get(0);
        List<String> projectIdsAsString = requestBody.get("projectIds");
        try {
            List<Long> projectIds = projectIdsAsString.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
             universityService.addProjectsToUniversity(Long.parseLong(universityId), projectIds);
            return"University added Project successfully";
        } catch (Exception e) {
            return  "Can not apply project: " + e.getMessage();
        }
    }

    @PostMapping("/applyProject")
    public String addProjectToUniversity(@Valid @RequestBody Map<String, String > requestBody) throws Exception {
        String universityId = requestBody.get("universityId");
        String projectIdsAsString = requestBody.get("projectId");
        try {
            universityService.addProjectToUniversity(Long.parseLong(universityId), Long.parseLong(projectIdsAsString) );
            return"University added Project successfully";
        } catch (Exception e) {
            return  "Can not apply project: " + e.getMessage();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertUniversity(@RequestBody UniversityDTO universityDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            UniversityEntity universityEntity = universityService.insertUniversity(universityDTO);

            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(null)
                            .message("Create university successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateUniversities/{id}")
    public ResponseEntity<String> updateUniversity(@PathVariable Long id, @RequestBody UniversityEntity updatedUniversity) {
        UniversityEntity updatedEntity = universityService.updateUniversity(id, updatedUniversity);
        if (updatedEntity != null) {
            return ResponseEntity.ok("University updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University not found");
    }

    @DeleteMapping("/deleteUniversities/{id}")
    public ResponseEntity<String> deleteUniversity(@PathVariable Long id) {
        boolean deleted = universityService.deleteUniversity(id);
        if (deleted) {
            return ResponseEntity.ok("University deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University not found");
    }

    @GetMapping("/getUniversities/{id}")
    public ResponseEntity<?> getUniversityById(@PathVariable Long id) {
        Optional<UniversityEntity> university = universityService.getUniversityById(id);
        UniversityEntity universityResponse = university.get();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(UniversityResponse.fromUniversity(universityResponse))
                        .message("Get university by id successfully")
                        .status(HttpStatus.OK)
                        .build());
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<UniversityEntity>> getAllUniversities() {
        List<UniversityEntity> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }
}
