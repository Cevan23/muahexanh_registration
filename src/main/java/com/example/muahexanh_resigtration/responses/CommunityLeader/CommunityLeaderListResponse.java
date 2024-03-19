package com.example.muahexanh_resigtration.responses.CommunityLeader;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;

import java.util.List;

public class CommunityLeaderListResponse {
    private List<CommunityLeaderResponse> communityLeaders;

    public static CommunityLeaderListResponse fromListCommunityLeader(List<CommunityLeaderEntity> communityLeaders) {
        List<CommunityLeaderResponse> communityLeaderResponses = communityLeaders.stream()
                .map(CommunityLeaderResponse::fromCommunityLeader)
                .toList();

        CommunityLeaderListResponse communityLeaderListResponse = new CommunityLeaderListResponse();
        communityLeaderListResponse.setCommunityLeaders(communityLeaderResponses);
        return communityLeaderListResponse;
    }

    private void setCommunityLeaders(List<CommunityLeaderResponse> communityLeaderResponses) {
        this.communityLeaders = communityLeaderResponses;
    }

    public List<CommunityLeaderResponse> getCommunityLeaders() {
        return this.communityLeaders;
    }
}
