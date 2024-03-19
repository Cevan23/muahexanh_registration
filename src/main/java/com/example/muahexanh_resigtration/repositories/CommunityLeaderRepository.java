package com.example.muahexanh_resigtration.repositories;


import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
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

    @Query("SELECT p FROM CommunityLeaderEntity c " +
            "JOIN c.projects p " +
            "WHERE c.id = :communityLeaderId AND LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<ProjectEntity> getProjectsByLeaderIDAndTitle(@Param("communityLeaderId") Long communityLeaderId, @Param("title") String title);;


    @Query("SELECT p FROM CommunityLeaderEntity c " +
            "JOIN c.projects p " +
            "WHERE c.id = :communityLeaderId AND p.status = :status")
    List<ProjectEntity> getProjectsByLeaderIDAndStatus(@Param("communityLeaderId") Long communityLeaderId, @Param("status") String status);

    @Query("SELECT c FROM CommunityLeaderEntity c WHERE c.email = :email" +
            " AND c.password = :password")
    Optional<CommunityLeaderEntity> loginCommunityLeader(@Param("email") String email,
                                         @Param("password") String password);
}
