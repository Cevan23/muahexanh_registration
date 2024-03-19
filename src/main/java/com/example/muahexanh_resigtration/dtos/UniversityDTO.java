package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityDTO extends UserDTO{

    @JsonProperty("university_name")
    private String universityName;

    @JsonProperty("projects")
    private List<ProjectDTO> projects;



}
