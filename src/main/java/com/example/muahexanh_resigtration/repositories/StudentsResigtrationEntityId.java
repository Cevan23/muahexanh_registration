package com.example.muahexanh_resigtration.repositories;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentsResigtrationEntityId implements Serializable {
    private Long projectId;
    private Long studentId;
}
