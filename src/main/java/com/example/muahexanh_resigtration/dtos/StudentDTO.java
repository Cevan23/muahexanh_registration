package com.example.muahexanh_resigtration.dtos;

import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StudentDTO extends UserDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("university")
    private UniversityDTO university;

    @JsonProperty( "GPA")
    private Float GPA;

    @JsonProperty("address")
    private String address;

    @JsonProperty("personal_transportation")
    private Boolean personalTransportation;
}
