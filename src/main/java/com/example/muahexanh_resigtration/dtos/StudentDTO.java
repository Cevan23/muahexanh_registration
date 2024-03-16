package com.example.muahexanh_resigtration.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("is_male")
    private Boolean isMale;

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
}
