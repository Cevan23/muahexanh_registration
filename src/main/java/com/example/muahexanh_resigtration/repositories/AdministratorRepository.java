package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.AdministratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {
    @Query("SELECT a FROM AdministratorEntity a WHERE a.id = :adminId")
    Optional<AdministratorEntity> getAdministratorById(@Param("adminId") Long adminId);

    @Query("SELECT a FROM AdministratorEntity a WHERE a.email = :email" +
            " AND a.password = :password")
    Optional<AdministratorEntity> loginAdministrator(@Param("email") String email,
                                         @Param("password") String password);
    Optional<AdministratorEntity> findByEmail(String email);
}
