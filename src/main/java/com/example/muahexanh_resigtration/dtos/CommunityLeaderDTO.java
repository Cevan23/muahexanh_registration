package com.example.muahexanh_resigtration.dtos;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityLeaderDTO extends UserDTO {

    @JsonProperty("projects")
    private List<ProjectEntity> projects;

}

