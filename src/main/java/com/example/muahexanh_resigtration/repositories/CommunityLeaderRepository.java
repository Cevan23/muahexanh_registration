package com.example.muahexanh_resigtration.repositories;


import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;

@Repository
public interface CommunityLeaderRepository extends JpaRepository<CommunityLeaderEntity, Long> {
//    List<ProjectEntity> findProjectsByCommunityLeaderId(Long leaderId);
    @Query("SELECT c FROM CommunityLeaderEntity c WHERE c.id = :communityLeaderId")
    Optional<CommunityLeaderEntity> getDetailCommunityLeader(@Param("communityLeaderId") Long communityLeaderId);
    // Tìm kiếm dự án trong danh sách dự án của một người dẫn dắt dựa trên tiêu đề
    @Query("SELECT p FROM CommunityLeaderEntity c JOIN c.projects p WHERE c.id = :communityLeaderId AND LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<ProjectEntity> findProjectsByLeaderIDAndTitleContainingIgnoreCase(@Param("communityLeaderId") Long communityLeaderId, @Param("title") String title);

    // Tìm kiếm dự án trong danh sách dự án của một người dẫn dắt dựa trên trạng thái
    @Query("SELECT p FROM CommunityLeaderEntity c JOIN c.projects p WHERE c.id = :communityLeaderId AND LOWER(p.status) LIKE LOWER(CONCAT('%', :status, '%'))")
    List<ProjectEntity> findProjectsByLeaderIDAndStatusContainingIgnoreCase(@Param("communityLeaderId") Long communityLeaderId, @Param("status") String status);
}
