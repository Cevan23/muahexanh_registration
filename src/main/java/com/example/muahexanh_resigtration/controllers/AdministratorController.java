package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.CommunityLeaderDTO;
import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.AdministratorDTO;

import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.responses.CommunityLeader.CommunityLeaderResponse;
import com.example.muahexanh_resigtration.responses.ResponseObject;

import com.example.muahexanh_resigtration.responses.Administrator.AdministratorListResponse;
import com.example.muahexanh_resigtration.responses.Administrator.AdministratorResponse;
import com.example.muahexanh_resigtration.services.Administrator.iAdministratorService;

import com.example.muahexanh_resigtration.services.CommunityLeader.iCommunityLeaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministratorController {
    private final iAdministratorService AdministratorService;
    private final iCommunityLeaderService communityLeaderService;

    @PostMapping("/login")
    public ResponseEntity<?> loginAdministrator(@Valid @RequestBody LoginDTO loginDTO) {
        {
            try {
                AdministratorEntity administratorResponse = AdministratorService.loginAdministrator(loginDTO);
                return ResponseEntity.ok(
                        ResponseObject.builder()
                                .data(AdministratorResponse.fromAdministrator(administratorResponse))
                                .message("Login successfully")
                                .status(HttpStatus.OK)
                                .build());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertAdministrator(
            @Valid @RequestBody AdministratorDTO AdministratorDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            AdministratorEntity administratorResponse = AdministratorService.insertAdministrator(AdministratorDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(AdministratorResponse.fromAdministrator(administratorResponse))
                            .message("Create administrator successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAdministrator() {
        try {
            List<AdministratorEntity> administratorResponse = AdministratorService.getAllAdministrator();
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(AdministratorListResponse.fromListAdministrator(administratorResponse))
                            .message("Get all administrator successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdministratorById(@PathVariable Long id) {
        try {
            AdministratorEntity administratorResponse = AdministratorService.getAdministratorById(id);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(AdministratorResponse.fromAdministrator(administratorResponse))
                            .message("Get administrator by id successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdministrator(
            @PathVariable Long id,
            @Valid @RequestBody AdministratorDTO AdministratorDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            AdministratorEntity administratorResponse = AdministratorService.updateAdministrator(id, AdministratorDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(AdministratorResponse.fromAdministrator(administratorResponse))
                            .message("Update administrator successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createCL")
    public ResponseEntity<?> createCommunityLeader(
            @Valid @RequestBody CommunityLeaderDTO communityLeaderDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            CommunityLeaderEntity communityLeaderResponse = communityLeaderService
                    .insertCommunityLeader(communityLeaderDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(CommunityLeaderResponse.fromCommunityLeader(communityLeaderResponse))
                            .message("Create student successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("updateCL/{id}")
    public ResponseEntity<?> updateCommunityLeader(
            @PathVariable Long id,
            @Valid @RequestBody CommunityLeaderDTO communityLeaderDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            CommunityLeaderEntity communityLeaderResponse = communityLeaderService.updateCommunityLeader(id,
                    communityLeaderDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(CommunityLeaderResponse.fromCommunityLeader(communityLeaderResponse))
                            .message("Update communityLeader successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteCL/{id}")
    public ResponseEntity<String> deleteCommunityLeader(@PathVariable Long id) {
        try {
            communityLeaderService.deleteCommunityLeader(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("CommunityLeader with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
