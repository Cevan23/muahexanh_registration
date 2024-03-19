package com.example.muahexanh_resigtration.responses.CommunityLeader;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.responses.Student.StudentResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityLeaderResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("projects")
    private List<ProjectEntity> projects;

    public static CommunityLeaderResponse fromCommunityLeader(CommunityLeaderEntity communityLeader) {
        return CommunityLeaderResponse
                .builder()
                .id(communityLeader.getId())
                .email(communityLeader.getEmail())
                .fullName(communityLeader.getFullName())
                .phoneNumber(communityLeader.getPhoneNumber())
                .role(communityLeader.getRole())
                .projects(communityLeader.getProjects())
                .build();
    }
}
