package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

public interface ICommunityLeadderService {
    CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception;
}
