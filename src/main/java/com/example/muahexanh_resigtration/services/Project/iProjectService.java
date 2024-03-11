package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;

import java.util.List;

public interface iProjectService {

    ProjectEntity insertProject(ProjectDTO projectDTO);

    ProjectEntity getProjectById(long id) throws Exception;

    List<ProjectEntity> getAllProject();

    ProjectEntity updateProject(long id, ProjectDTO projectDTO) throws Exception;

    void deleteProject(long id);
}
