package com.example.muahexanh_resigtration.entities;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public  abstract class UserEntity {

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

}