package com.example.muahexanh_resigtration.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "max_project_members")
    private int maxProjectMembers;

    @Column(name = "max_school_registrations")
    private int maxSchoolRegistrations;

    @Column(name = "status", columnDefinition = "varchar(255) check (status in ('pending', 'accepted', 'rejected'))")
    private String status;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @ManyToMany(targetEntity= StudentEntity.class)
    private List<StudentEntity> students;

}
