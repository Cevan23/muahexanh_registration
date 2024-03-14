package com.example.muahexanh_resigtration.entities;
import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "students")
public class StudentEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversityEntity university;

    @Column(name = "address")
    private String address;

    @Column(name = "personal_description")
    private String personalDescription;

    @Column(name = "gender")
    private Boolean gender;

}
