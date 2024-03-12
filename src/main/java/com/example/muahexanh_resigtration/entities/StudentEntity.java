package com.example.muahexanh_resigtration.entities;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity=UniversityEntity.class)
    private  UniversityEntity university;

    @Column(name = "GPA")
    private Float GPA;

    @Column(name = "address")
    private String address;

    @Column(name = "personal_transportation")
    private Boolean personalTransportation;
}
