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
    @ManyToMany(targetEntity=ProjectEntity.class)
    private List<ProjectEntity> projects;

    @ManyToOne(targetEntity=UniversityEntity.class)
    private  UniversityEntity university;

    private String studentID;
    private Float GPA;
}
