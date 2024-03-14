package com.example.muahexanh_resigtration.services.CommunityLeader;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommunityLeaderService implements ICommunityLeaderService{
    private final CommunityLeaderRepository communityLeaderRepository;

    @Override
    public List<ProjectEntity> searchProjectByTitle(Long id,String title) {
        return communityLeaderRepository.findProjectsByLeaderIDAndTitleContainingIgnoreCase(id,title);
    }

    @Override
    public List<ProjectEntity> filterProjectsByStatus(Long id,String status) {
        return communityLeaderRepository.findProjectsByLeaderIDAndStatusContainingIgnoreCase(id,status);
    }

    @Override
    public CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception {
        Optional<CommunityLeaderEntity> optionalCommunityLeader = communityLeaderRepository.getDetailCommunityLeader(id);
        if(optionalCommunityLeader.isPresent()) {
            return optionalCommunityLeader.get();
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }
}
