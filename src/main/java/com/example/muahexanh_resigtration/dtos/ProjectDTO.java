package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    @JsonProperty("title")
    @NotBlank(message = "Project title is required")
    private String title;

    @JsonProperty("description")
    @NotBlank(message = "Project description is required")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("maximumStudents")
    private int maximumStudents;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("students")
    private List<StudentDTO> students;

}
