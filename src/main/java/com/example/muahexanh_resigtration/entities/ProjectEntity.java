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

    @Column(name = "max_school_registration_members")
    private int maxSchoolRegistrationMembers;

    @Column(name = "status", columnDefinition = "varchar(255) check (status in ('pending', 'accepted', 'done', 'cancelled'))")
    private String status;

    @Column(name = "img_root")
    private String imgRoot;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @ManyToMany(targetEntity= StudentEntity.class)
    private List<StudentEntity> students;

}
