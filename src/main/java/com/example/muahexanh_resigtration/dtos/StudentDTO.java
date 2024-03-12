package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StudentDTO extends UserDTO {
    @JsonProperty("fullname")
    private String fullname;

}
