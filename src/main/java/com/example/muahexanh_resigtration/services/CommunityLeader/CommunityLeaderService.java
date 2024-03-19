package com.example.muahexanh_resigtration.services.CommunityLeader;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.exceptions.InvalidParamException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityLeaderService implements iCommunityLeaderService {
    private final CommunityLeaderRepository communityLeaderRepository;

    @Override
    public List<ProjectEntity> searchProjectByTitle(Long id, String title) {
        Optional<CommunityLeaderEntity> communityLeaderOptional = communityLeaderRepository
                .getDetailCommunityLeader(id);

        if (communityLeaderOptional.isPresent()) {
            CommunityLeaderEntity communityLeader = communityLeaderOptional.get();
            List<ProjectEntity> projects = communityLeader.getProjects();

            List<ProjectEntity> filteredProjects = projects.stream()
                    .filter(project -> project.getTitle().contains(title))
                    .collect(Collectors.toList());

            return filteredProjects;
        } else {
            // Xử lý trường hợp không tìm thấy community leader với id tương ứng
            // Có thể throw một exception hoặc trả về null tuỳ thuộc vào logic của bạn
            return null;
        }
    }

    @Override
    public List<ProjectEntity> filterProjectsByStatus(Long id, String status) {

        return communityLeaderRepository.getProjectsByLeaderIDAndStatus(id, status);
    }

    @Override
    public CommunityLeaderEntity getCommunityLeaderById(long id) throws Exception {
        Optional<CommunityLeaderEntity> optionalCommunityLeader = communityLeaderRepository
                .getDetailCommunityLeader(id);
        if (optionalCommunityLeader.isPresent()) {
            return optionalCommunityLeader.get();
        }
        throw new DataNotFoundException("Cannot find project with id =" + id);
    }

    @Override
    public CommunityLeaderEntity loginCommunityLeader(LoginDTO LoginDTO) throws Exception {
        Optional<CommunityLeaderEntity> communityLeader = communityLeaderRepository
                .loginCommunityLeader(LoginDTO.getEmail(), LoginDTO.getPassword());

        // Bcrypt was not applied as database password in database didn't encode.
        if (communityLeader.isEmpty())
            throw new InvalidParamException("Email or password is not correct");

        return communityLeader.get();
    }
}
