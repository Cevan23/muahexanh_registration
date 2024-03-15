package com.example.muahexanh_resigtration.responses.Project;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProjectListResponse {

    private List<ProjectResponse> projects;

    public static ProjectListResponse fromListProject(List<ProjectEntity> projects) {
        List<ProjectResponse> projectResponses = projects.stream()
                .map(ProjectResponse::fromProject)
                .collect(Collectors.toList());

        ProjectListResponse projectListResponse = new ProjectListResponse();
        projectListResponse.setProjects(projectResponses);
        return projectListResponse;
    }

    private void setProjects(List<ProjectResponse> projectResponses) {
        this.projects = projectResponses;
    }

}