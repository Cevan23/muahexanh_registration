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
public class UniversityDTO extends UserDTO{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("projects")
    private List<ProjectDTO> projects;

    @JsonProperty("university_name")
    private String universityName;

}
