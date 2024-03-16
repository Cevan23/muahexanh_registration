package com.example.muahexanh_resigtration.services.CommunityLeader;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;

import java.util.List;

public interface iCommunityLeaderService {

    List<ProjectEntity> searchProjectByTitle(Long id,String title);

    List<ProjectEntity> filterProjectsByStatus(Long id,String status);
    CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception;
}
