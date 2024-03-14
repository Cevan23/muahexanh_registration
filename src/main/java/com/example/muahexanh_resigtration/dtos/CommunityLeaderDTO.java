package com.example.muahexanh_resigtration.dtos;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommunityLeaderDTO extends UserDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("projects")
    private List<ProjectEntity> projects;

}
