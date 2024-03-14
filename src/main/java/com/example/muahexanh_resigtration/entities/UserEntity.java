package com.example.muahexanh_resigtration.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class UserEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "role", columnDefinition = "varchar(255) check (role in ('Student', 'University', 'CommunityLeader', 'Administrator'))")
    private String role;

}