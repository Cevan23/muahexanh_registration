package com.example.muahexanh_resigtration.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "universities")
public class UniversityEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(targetEntity=ProjectEntity.class)
    private List<ProjectEntity> projects;

    @Column(name = "university_name")
    private String universityName;
}
