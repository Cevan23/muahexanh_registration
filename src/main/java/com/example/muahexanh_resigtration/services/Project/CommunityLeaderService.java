package com.example.muahexanh_resigtration.services.Project;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class CommunityLeaderService implements ICommunityLeadderService{
    private final CommunityLeaderRepository communityLeaderRepository;
    @Override
    public CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception {
        Optional<CommunityLeaderEntity> optionalCommunityLeader = communityLeaderRepository.getDetailCommunityLeader(id);
        if(optionalCommunityLeader.isPresent()) {
            return optionalCommunityLeader.get();
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }
}
