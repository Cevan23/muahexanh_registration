package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.responses.CommunityLeader.CommunityLeaderResponseUser;
import com.example.muahexanh_resigtration.responses.ResponseObject;
import com.example.muahexanh_resigtration.services.CommunityLeader.iCommunityLeaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/communityleader")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityLeaderController {
    private final iCommunityLeaderService communityLeaderService;

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> SearchProjectByTitle(
            @Valid @RequestParam("id") Long leaderId,
            @Valid @RequestParam("title") String title

    ) {
        try {

            List<ProjectEntity> listProjects = communityLeaderService.searchProjectByTitle(leaderId, title);
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

    @GetMapping("/filterByStatus")
    public ResponseEntity<?> filterProjectsByStatus(@Valid @RequestParam("id") Long leaderId,
            @Valid @RequestParam("status") String status) {
        try {
            List<ProjectEntity> filteredProjects = communityLeaderService.filterProjectsByStatus(leaderId, status);
            return ResponseEntity.ok(filteredProjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginCommunityLeader(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            CommunityLeaderResponseUser communityLeader = communityLeaderService.loginCommunityLeader(loginDTO);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", communityLeader.getId());
            dataMap.put("fullName", communityLeader.getFullName());
            dataMap.put("phoneNumber", communityLeader.getPhoneNumber());
            dataMap.put("email", communityLeader.getEmail());

            // return a community leader id
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Found community leader account")
                            .status(HttpStatus.OK)
                            .data(dataMap)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
