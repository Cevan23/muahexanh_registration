package com.example.muahexanh_resigtration.services.CommunityLeader;

import com.example.muahexanh_resigtration.dtos.CommunityLeaderDTO;
import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.responses.CommunityLeader.CommunityLeaderResponseUser;

import java.util.List;

public interface iCommunityLeaderService {

    List<ProjectEntity> searchProjectByTitle(Long id, String title);

    List<ProjectEntity> filterProjectsByStatus(Long id, String status);

    CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception;

    CommunityLeaderEntity insertCommunityLeader(CommunityLeaderDTO communityLeaderDTO) throws Exception;

    CommunityLeaderEntity updateCommunityLeader(long id, CommunityLeaderDTO communityLeaderDTO) throws Exception;

    void deleteCommunityLeader(Long id) throws Exception;

    CommunityLeaderResponseUser loginCommunityLeader(LoginDTO loginDTO) throws Exception;
}
