package com.example.muahexanh_resigtration.services.CommunityLeader;
import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;

import java.util.List;

public interface iCommunityLeaderService {

    List<ProjectEntity> searchProjectByTitle(Long id,String title);

    List<ProjectEntity> filterProjectsByStatus(Long id,String status);
    CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception;

    CommunityLeaderEntity loginCommunityLeader(LoginDTO LoginDTO) throws Exception;
}
