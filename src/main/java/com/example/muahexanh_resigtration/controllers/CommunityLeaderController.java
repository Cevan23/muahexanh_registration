package com.example.muahexanh_resigtration.controllers;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/communityleader")
@RequiredArgsConstructor
public class CommunityLeaderController {
    private final iCommunityLeaderService communityLeaderService;
    @GetMapping("/search/{id}/{title}")
    public ResponseEntity<ResponseObject> SearchProject(
            @Valid @PathVariable("id") Long leaderId,
            @Valid @PathVariable("title") String title,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                // Nếu có lỗi, trả về danh sách các lỗi
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                        .message("Validation failed")
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
            }
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
                                                    @Valid @PathVariable("status") String status,
                                                    BindingResult result) {
        try {
            List<ProjectEntity> filteredProjects = communityLeaderService.filterProjectsByStatus(leaderId,status);
            return ResponseEntity.ok(filteredProjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
