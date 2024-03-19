package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface iProjectService {

    ProjectEntity insertProject(ProjectDTO projectDTO) throws ParseException;

    ProjectEntity getProjectById(long id) throws Exception;

    List<Map<String, Object>> getAllProjects();

    List<ProjectEntity> getAllProjectByLeaderId(long id) throws Exception;

    List<ProjectEntity> getProjectByStudentId(long studentId) throws Exception;

    Map<String, Object> getProjectByLeaderIdAndProjectId(long leaderId,long projectId) throws Exception;

    ProjectEntity updateProject(long id, ProjectDTO projectDTO) throws Exception;

    void deleteProject(long id);
}
