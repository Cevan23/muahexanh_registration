package com.example.muahexanh_resigtration.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "community_leaders")
public class CommunityLeaderEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity=ProjectEntity.class)
    private List<ProjectEntity> projects;
}
