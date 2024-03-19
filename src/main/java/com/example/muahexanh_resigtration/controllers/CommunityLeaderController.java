package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.AdministratorDTO;
import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.responses.Administrator.AdministratorResponse;
import com.example.muahexanh_resigtration.responses.ResponseObject;
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
@RequestMapping("/api/communityleader")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityLeaderController {
    private final iCommunityLeaderService communityLeaderService;
    @GetMapping("/search/{id}/{title}")
    public ResponseEntity<ResponseObject> SearchProjectByTitle(
            @Valid @PathVariable("id") Long leaderId,
            @Valid @PathVariable("title") String title

    ) {
        try {

            List<ProjectEntity> listProjects = communityLeaderService.searchProjectByTitle(leaderId,title);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(listProjects)
                    .message("Get detail project successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message("An error occurred: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @GetMapping("/filterByStatus/{id}/{status}")
    public ResponseEntity<?> filterProjectsByStatus(@Valid @PathVariable("id") Long leaderId,
                                                    @Valid @PathVariable("status") String status
                                                   ) {
        try {
            List<ProjectEntity> filteredProjects = communityLeaderService.filterProjectsByStatus(leaderId,status);
            return ResponseEntity.ok(filteredProjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
