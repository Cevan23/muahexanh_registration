package com.example.muahexanh_resigtration.responses.Student;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("gender")
    private Boolean gender;

    @JsonProperty("address")
    private String address;

    @JsonProperty("personal_description")
    private String personalDescription;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("university_name")
    private String universityName;

    public static StudentResponse fromStudent(StudentEntity student) {
        return StudentResponse
                .builder()
                .id(student.getId())
                .email(student.getEmail())
                .fullName(student.getFullName())
                .gender(student.getGender())
                .address(student.getAddress())
                .personalDescription(student.getPersonalDescription())
                .phoneNumber(student.getPhoneNumber())
                .role(student.getRole())
                .universityName(student.getUniversityName())
                .build();
    }
}
