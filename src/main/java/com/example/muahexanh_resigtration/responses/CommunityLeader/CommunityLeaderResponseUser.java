package com.example.muahexanh_resigtration.responses.CommunityLeader;

import com.example.muahexanh_resigtration.entities.CommunityLeaderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityLeaderResponseUser {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    public static CommunityLeaderResponseUser fromCommunityLeaderUser(CommunityLeaderEntity communityLeader) {
        return CommunityLeaderResponseUser
                .builder()
                .id(communityLeader.getId())
                .email(communityLeader.getEmail())
                .fullName(communityLeader.getFullName())
                .phoneNumber(communityLeader.getPhoneNumber())
                .role(communityLeader.getRole())
                .build();
    }
}
