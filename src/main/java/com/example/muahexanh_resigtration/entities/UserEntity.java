package com.example.muahexanh_resigtration.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    private String username;
    private String password;
    private String role;

}
