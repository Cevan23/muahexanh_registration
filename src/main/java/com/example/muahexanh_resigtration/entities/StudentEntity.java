package com.example.muahexanh_resigtration.entities;

import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "students")
public class StudentEntity extends UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "university_name")
    private String universityName;

    @Column(name = "address")
    private String address;

    @Column(name = "personal_description")
    private String personalDescription;

    @Column(name = "gender")
    private Boolean gender;

}
