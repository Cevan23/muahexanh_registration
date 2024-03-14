package com.example.muahexanh_resigtration.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "community_leaders")
public class CommunityLeaderEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(targetEntity=ProjectEntity.class)
    private List<ProjectEntity> projects;
}
