package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StudentDTO extends UserDTO {
    @JsonProperty("fullname")
    private String fullname;

}
