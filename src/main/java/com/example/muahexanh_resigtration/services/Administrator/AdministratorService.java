package com.example.muahexanh_resigtration.services.Administrator;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.AdministratorDTO;
import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.AdministratorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdministratorService implements iAdministratorService {
    private final AdministratorRepository administratorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AdministratorEntity insertAdministrator(AdministratorDTO AdministratorDTO) throws ParseException {
        AdministratorEntity newAdministrator = AdministratorEntity
                .builder()
                .email(AdministratorDTO.getEmail())
                .fullName(AdministratorDTO.getFullName())
                .password(passwordEncoder.encode(AdministratorDTO.getPassword()))
                .phoneNumber(AdministratorDTO.getPhoneNumber())
                .role("Administrator")
                .build();
                
        return administratorRepository.save(newAdministrator);
    }

    @Override
    public AdministratorEntity loginAdministrator(LoginDTO loginDTO) throws Exception {
        Optional<AdministratorEntity> administrator = administratorRepository.findByEmail(loginDTO.getEmail());
        if (administrator.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), administrator.get().getPassword())) {
            throw new DataNotFoundException("Invalid email or password");
        }
        return administrator.get();
    }

    @Override
    public AdministratorEntity updateAdministrator(long id, AdministratorDTO AdministratorDTO) throws Exception {
        Optional<AdministratorEntity> administrator = administratorRepository.findById(id);
        if (administrator.isEmpty()) {
            throw new DataNotFoundException("Cannot find administrator with id = " + id);
        }
        AdministratorEntity administratorEntity = administrator.get();

        if (AdministratorDTO.getEmail() != null) {
            administratorEntity.setEmail(AdministratorDTO.getEmail());
        }
        if (AdministratorDTO.getFullName() != null) {
            administratorEntity.setFullName(AdministratorDTO.getFullName());
        }
        if (AdministratorDTO.getPassword() != null) {
            administratorEntity.setPassword(passwordEncoder.encode(AdministratorDTO.getPassword()));
        }
        if (AdministratorDTO.getPhoneNumber() != null) {
            administratorEntity.setPhoneNumber(AdministratorDTO.getPhoneNumber());
        }
        return administratorRepository.save(administratorEntity);
    }

    @Override
    public void deleteAdministrator(long id) throws ParseException {

    }

    @Override
    public AdministratorEntity getAdministratorById(long id) throws Exception {
        Optional<AdministratorEntity> administrator = administratorRepository.findById(id);
        if (administrator.isEmpty()) {
            throw new Exception("Administrator not found");
        }
        return administrator.get();
    }

    @Override
    public List<AdministratorEntity> getAllAdministrator() {
        return administratorRepository.findAll();
    }
}
