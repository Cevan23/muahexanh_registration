package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.UniversityProjectRequest;
import com.example.muahexanh_resigtration.entities.UniversityEntity;

import com.example.muahexanh_resigtration.services.University.iUniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @PostMapping("/creatUniversities")
    public ResponseEntity<String> addUniversity(@RequestBody UniversityEntity university) {
        universityService.addUniversity(university);
        return ResponseEntity.ok("University added successfully");
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
    public ResponseEntity<UniversityEntity> getUniversityById(@PathVariable Long id) {
        Optional<UniversityEntity> university = universityService.getUniversityById(id);
        return university.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<UniversityEntity>> getAllUniversities() {
        List<UniversityEntity> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }
}
