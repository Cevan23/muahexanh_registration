package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService implements iProjectService {

    private final ProjectRepository projectRepository;
    private final CommunityLeaderRepository communityLeaderRepository;
    @Override
    public ProjectEntity insertProject(ProjectDTO projectDTO) {
        ProjectEntity newProject = ProjectEntity
                .builder()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .status(projectDTO.getStatus())
                .build();
        return projectRepository.save(newProject);
    }

    @Override
    public ProjectEntity getProjectById(long id) throws Exception {
        Optional<ProjectEntity> optionalProduct = projectRepository.getDetailProject(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }

    @Override
    public List<ProjectEntity> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public List<ProjectEntity> getAllProjectById(long id) {
        Optional<List<ProjectEntity>> optionalProject = projectRepository.getAllProjectByLeaderId(id);
        return optionalProject.get();

    }

    @Override
    public ProjectEntity updateProject(long id, ProjectDTO projectDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteProject(long id) {

    }
}
