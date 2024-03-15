package com.example.muahexanh_resigtration.responses.Administrator;

import com.example.muahexanh_resigtration.entities.AdministratorEntity;

import java.util.List;

public class AdministratorListResponse {

    private List<AdministratorResponse> administrators;

    public static AdministratorListResponse fromListAdministrator(List<AdministratorEntity> administrators) {
        List<AdministratorResponse> administratorResponses = administrators.stream()
                .map(AdministratorResponse::fromAdministrator)
                .toList();

        AdministratorListResponse administratorListResponse = new AdministratorListResponse();
        administratorListResponse.setAdministrators(administratorResponses);
        return administratorListResponse;
    }

    private void setAdministrators(List<AdministratorResponse> administratorResponses) { this.administrators = administratorResponses; }

    public List<AdministratorResponse> getAdministrators() {
        return this.administrators;
    }
}