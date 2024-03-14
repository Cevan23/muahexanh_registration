package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {

    @JsonProperty("university_name")
    private long universityName;

    @JsonProperty("gender")
    private Boolean gender;

    @JsonProperty("address")
    private String address;

    @JsonProperty("personal_description")
    private String personalDescription;
}
