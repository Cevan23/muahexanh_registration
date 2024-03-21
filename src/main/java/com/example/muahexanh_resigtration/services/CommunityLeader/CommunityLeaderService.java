package com.example.muahexanh_resigtration.services.CommunityLeader;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.CommunityLeaderDTO;

import com.example.muahexanh_resigtration.dtos.ProjectDTO;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.CommunityLeaderRepository;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import com.example.muahexanh_resigtration.responses.CommunityLeader.CommunityLeaderResponse;
import com.example.muahexanh_resigtration.responses.CommunityLeader.CommunityLeaderResponseUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityLeaderService implements iCommunityLeaderService {
    private final CommunityLeaderRepository communityLeaderRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectRepository projectRepository;

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
    public CommunityLeaderEntity insertCommunityLeader(CommunityLeaderDTO communityLeaderDTO) throws Exception {
        Optional<CommunityLeaderEntity> existingCommunityLeader = communityLeaderRepository
                .findByEmail(communityLeaderDTO.getEmail());
        if (existingCommunityLeader.isPresent()) {
            throw new Exception("Email already exists");
        }

        CommunityLeaderEntity newCommunityLeader = CommunityLeaderEntity
                .builder()
                .email(communityLeaderDTO.getEmail())
                .fullName(communityLeaderDTO.getFullName())
                .password(passwordEncoder.encode(communityLeaderDTO.getPassword()))
                .phoneNumber(communityLeaderDTO.getPhoneNumber())
                .role("CommunityLeader")
                .projects(communityLeaderDTO.getProjects())
                .build();

        return communityLeaderRepository.save(newCommunityLeader);
    }

    @Override
    public CommunityLeaderEntity updateCommunityLeader(long id, CommunityLeaderDTO communityLeaderDTO)
            throws Exception {
        Optional<CommunityLeaderEntity> communityLeader = communityLeaderRepository.findById(id);
        if (communityLeader.isEmpty()) {
            throw new DataNotFoundException("Cannot find communityLeader with id = " + id);
        }
        CommunityLeaderEntity communityLeaderEntity = communityLeader.get();

        if (communityLeaderDTO.getEmail() != null) {
            communityLeaderEntity.setEmail(communityLeaderDTO.getEmail());
        }
        if (communityLeaderDTO.getFullName() != null) {
            communityLeaderEntity.setFullName(communityLeaderDTO.getFullName());
        }
        if (communityLeaderDTO.getPassword() != null) {
            communityLeaderEntity.setPassword(passwordEncoder.encode(communityLeaderDTO.getPassword()));
        }

        if (communityLeaderDTO.getPhoneNumber() != null) {
            communityLeaderEntity.setPhoneNumber(communityLeaderDTO.getPhoneNumber());
        }

        if (communityLeaderDTO.getProjects() != null) {
            communityLeaderEntity.setProjects(communityLeaderDTO.getProjects());
        }
        return communityLeaderRepository.save(communityLeaderEntity);
    }

    @Override
    public void deleteCommunityLeader(Long id) throws Exception {
        Optional<CommunityLeaderEntity> communityLeader = communityLeaderRepository.findById(id);
        if (communityLeader.isEmpty()) {
            throw new DataNotFoundException("Cannot find communityLeader with id = " + id);
        }
        CommunityLeaderEntity communityLeaderEntity = communityLeader.get();
        communityLeaderRepository.delete(communityLeaderEntity);

    }

    @Override
    public CommunityLeaderResponseUser loginCommunityLeader(LoginDTO loginDTO) throws Exception {
        Optional<CommunityLeaderEntity> optionalCommunityLeader = communityLeaderRepository
                .findByEmail(loginDTO.getEmail());
        if (optionalCommunityLeader.isEmpty()
                || !passwordEncoder.matches(loginDTO.getPassword(), optionalCommunityLeader.get().getPassword())) {
            throw new DataNotFoundException("Invalid email or password");
        }

        CommunityLeaderEntity communityLeader = optionalCommunityLeader.get(); // Trích xuất đối tượng từ Optional

        // Tạo một đối tượng CommunityLeaderResponseUser từ dữ liệu của
        // CommunityLeaderEntity
        return CommunityLeaderResponseUser.fromCommunityLeaderUser(communityLeader);
    }

    @Override
    public  CommunityLeaderEntity createProjectOfComCommunityLeader(Long communityLeaderId, ProjectDTO projectDTO ) throws ParseException
    {
        if (communityLeaderId == null) {
            throw new IllegalArgumentException("Community leader ID cannot be null");
        }
        Optional<CommunityLeaderEntity> optionalCommunityLeader = communityLeaderRepository.getDetailCommunityLeader(communityLeaderId);
        if (optionalCommunityLeader.isPresent()) {
            CommunityLeaderEntity communityLeader = optionalCommunityLeader.get();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDateStart, sqlDateEnd;
            java.util.Date parsed = format.parse(projectDTO.getDateStart());
            sqlDateStart = new java.sql.Date(parsed.getTime());

            parsed = format.parse(projectDTO.getDateEnd());
            sqlDateEnd = new java.sql.Date(parsed.getTime());
            // Tạo một entity mới từ dữ liệu DTO
            ProjectEntity newProject = ProjectEntity
                    .builder()
                    .title(projectDTO.getTitle())
                    .address(projectDTO.getAddress())
                    .description(projectDTO.getDescription())
                    .status(projectDTO.getStatus())
                    .dateStart(sqlDateStart)
                    .dateEnd(sqlDateEnd)
                    .imgRoot(projectDTO.getImgRoot())
                    .maxProjectMembers(projectDTO.getMaxProjectMembers())
                    .maxSchoolRegistrationMembers(projectDTO.getMaxSchoolRegistrationMembers())
                    .build();
            projectRepository.save(newProject);
            // Thêm project mới vào danh sách projects của Community Leader
            List<ProjectEntity> projects = communityLeader.getProjects();

            projects.add(newProject);
            communityLeader.setProjects(projects);

            // Lưu lại thông tin Community Leader đã được cập nhật với project mới
           return  communityLeaderRepository.save(communityLeader);
        }
        else {
        // Nếu không tìm thấy community leader với id tương ứng
            throw new IllegalArgumentException("Community leader not found with ID: " + communityLeaderId);
        }
    }

}
