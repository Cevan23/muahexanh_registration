package com.example.muahexanh_resigtration.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityProjectRequest {
    private Long universityId;
    private List<Long> projectIds;
}
