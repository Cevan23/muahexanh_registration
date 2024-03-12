package com.example.muahexanh_resigtration.responses.Project;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("leader_id")
    private Long leaderId;

    public static ProjectResponse fromProduct(ProjectEntity project) {
        ProjectResponse projectResponse =  ProjectResponse
                .builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
        return projectResponse;
    }



}
