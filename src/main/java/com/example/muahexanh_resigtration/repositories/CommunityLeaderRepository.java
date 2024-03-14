package com.example.muahexanh_resigtration.repositories;


import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityLeaderRepository extends JpaRepository<CommunityLeaderEntity, Long> {
    @Query("SELECT c FROM CommunityLeaderEntity c WHERE c.id = :communityLeaderId")

    Optional<CommunityLeaderEntity> getDetailCommunityLeader(@Param("communityLeaderId") Long communityLeaderId);
}
