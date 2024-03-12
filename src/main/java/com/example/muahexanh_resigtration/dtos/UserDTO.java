package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
