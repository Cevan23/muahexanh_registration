package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.StudentDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.responses.Project.ProjectListResponse;
import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.responses.Student.StudentListResponse;
import com.example.muahexanh_resigtration.responses.Student.StudentResponse;
import com.example.muahexanh_resigtration.services.Student.iStudentService;
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
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final iStudentService StudentService;

    @PostMapping("")
    public ResponseEntity<?> insertStudent(
            @Valid @RequestBody StudentDTO StudentDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            StudentEntity studentResponse = StudentService.insertStudent(StudentDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(StudentResponse.fromStudent(studentResponse))
                            .message("Create student successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllStudent() {
        try {
            List<StudentEntity> studentResponse = StudentService.getAllStudent();
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(StudentListResponse.fromListStudent(studentResponse))
                            .message("Get all student successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            StudentEntity studentResponse = StudentService.getStudentById(id);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(StudentResponse.fromStudent(studentResponse))
                            .message("Get student by id successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO StudentDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            StudentEntity studentResponse = StudentService.updateStudent(id, StudentDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(StudentResponse.fromStudent(studentResponse))
                            .message("Update student successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter/{address}")
    public ResponseEntity<?> getAllStudentFilterByAddress(@PathVariable String address) {
        try {
            Map<String, Object> studentMap = StudentService.getAllStudentContainAddress(address);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(studentMap)
                            .message("Get student by containing address successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
