package com.example.muahexanh_resigtration.repositories;


import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLeaderRepository extends JpaRepository<CommunityLeaderEntity, Long> {
}
