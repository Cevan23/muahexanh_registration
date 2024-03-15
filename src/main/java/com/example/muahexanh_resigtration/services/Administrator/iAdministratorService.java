package com.example.muahexanh_resigtration.services.Administrator;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.AdministratorDTO;
import com.example.muahexanh_resigtration.entities.AdministratorEntity;

import java.text.ParseException;
import java.util.List;

public interface iAdministratorService {
    AdministratorEntity insertAdministrator(AdministratorDTO AdministratorDTO) throws ParseException;

    AdministratorEntity loginAdministrator(LoginDTO LoginDTO) throws Exception;

    AdministratorEntity updateAdministrator(long id, AdministratorDTO AdministratorDTO) throws Exception;

    void deleteAdministrator(long id) throws ParseException;

    AdministratorEntity getAdministratorById(long id) throws Exception;

    List<AdministratorEntity> getAllAdministrator();
}
