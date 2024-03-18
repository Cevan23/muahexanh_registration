package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.UniversityProjectRequest;
import com.example.muahexanh_resigtration.entities.UniversityEntity;

import com.example.muahexanh_resigtration.services.University.iUniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UniversityController {

    private final iUniversityService universityService;
    @PostMapping("/universities/projects")
    public ResponseEntity<?> addProjectsToUniversity(@RequestBody UniversityProjectRequest request) throws Exception {
        Long universityId = request.getUniversityId();
        List<Long> projectIds = request.getProjectIds();
        try {
             universityService.addProjectsToUniversity(universityId,projectIds);
            return ResponseEntity.ok("University added Project successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
