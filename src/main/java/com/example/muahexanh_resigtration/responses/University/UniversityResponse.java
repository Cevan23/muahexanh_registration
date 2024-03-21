package com.example.muahexanh_resigtration.responses.University;

import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversityResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("university_name")
    private String universityName;

    public static com.example.muahexanh_resigtration.responses.University.UniversityResponse fromUniversity(UniversityEntity university) {
        return com.example.muahexanh_resigtration.responses.University.UniversityResponse
                .builder()
                .id(university.getId())
                .email(university.getEmail())
                .fullName(university.getFullName())
                .phoneNumber(university.getPhoneNumber())
                .role(university.getRole())
                .universityName(university.getUniversityName())
                .build();
    }
}
