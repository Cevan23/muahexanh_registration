package com.example.muahexanh_resigtration.responses.Administrator;

import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministratorResponse {

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

    public static AdministratorResponse fromAdministrator(AdministratorEntity administrator) {
        return AdministratorResponse
                .builder()
                .id(administrator.getId())
                .email(administrator.getEmail())
                .fullName(administrator.getFullName())
                .phoneNumber(administrator.getPhoneNumber())
                .role(administrator.getRole())
                .build();
    }
}
