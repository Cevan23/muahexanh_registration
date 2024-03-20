package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.responses.Student.StudentResponse;
import com.example.muahexanh_resigtration.services.Administrator.iAdministratorService;
import com.example.muahexanh_resigtration.services.CommunityLeader.iCommunityLeaderService;
import com.example.muahexanh_resigtration.services.Student.iStudentService;
import com.example.muahexanh_resigtration.services.University.iUniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final iStudentService StudentService;
    private final iUniversityService universityService;
    private final iCommunityLeaderService communityLeaderService;
    private final iAdministratorService AdministratorService;
    @PostMapping("/")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Attempt to login as a student
            StudentEntity studentResponse = StudentService.loginStudent(loginDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(StudentResponse.fromStudent(studentResponse))
                            .message("Login successfully as a student")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            // If login as a student fails, try to login as a university
            try {
                UniversityEntity university = universityService.loginUniversity(loginDTO);
                return ResponseEntity.ok(
                        ResponseObject.builder()
                                .data(university)
                                .message("Login successfully as a university")
                                .status(HttpStatus.OK)
                                .build());
            } catch (Exception ex) {
                // If login as a university fails, try to login as a community leader
                try {
                    CommunityLeaderEntity communityLeader = communityLeaderService.loginCommunityLeader(loginDTO);
                    return ResponseEntity.ok(
                            ResponseObject.builder()
                                    .data(communityLeader)
                                    .message("Login successfully as a community leader")
                                    .status(HttpStatus.OK)
                                    .build());
                } catch (Exception exc) {
                    // If login as a community leader fails, try to login as an administrator
                    try {
                        AdministratorEntity administrator = AdministratorService.loginAdministrator(loginDTO);
                        return ResponseEntity.ok(
                                ResponseObject.builder()
                                        .data(administrator)
                                        .message("Login successfully as an administrator")
                                        .status(HttpStatus.OK)
                                        .build());
                    } catch (Exception exce) {
                        // If all attempts fail, return an UNAUTHORIZED status
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                    }
                }
            }
        }
    }

}
