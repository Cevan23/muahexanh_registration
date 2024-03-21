package com.example.muahexanh_resigtration.entities;

import com.example.muahexanh_resigtration.repositories.StudentsResigtrationEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "students_resigtration")
public class StudentsResigtrationEntity {

    @EmbeddedId
    private StudentsResigtrationEntityId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("projectId")
    private ProjectEntity project;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId("studentId")
    private StudentEntity student;

    @Column(name = "registration_status", columnDefinition = "varchar(255) check (registration_status in ('pending', 'accepted'))")
    private String registration_status;

}
