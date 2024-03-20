package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResigtrationDTO {
    @JsonProperty("project")
    private ProjectDTO project;
    
    @JsonProperty("student")
    private StudentDTO student;

    @JsonProperty("registration_status")
    private String registration_status;
}
