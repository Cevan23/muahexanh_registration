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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("max_project_members")
    private int maxProjectMembers;

    @JsonProperty("max_school_registrations")
    private int maxSchoolRegistrations;

    public static ProjectResponse fromProject(ProjectEntity project) {
        return ProjectResponse
                .builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .dateStart(String.valueOf(project.getDateStart()))
                .dateEnd(String.valueOf(project.getDateEnd()))
                .maxProjectMembers(project.getMaxProjectMembers())
                .maxSchoolRegistrations(project.getMaxSchoolRegistrations())
                .build();
    }
}
