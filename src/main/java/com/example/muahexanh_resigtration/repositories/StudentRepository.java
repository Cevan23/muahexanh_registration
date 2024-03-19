package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query("SELECT s FROM StudentEntity s WHERE s.id = :studentId")
    Optional<StudentEntity> getStudentById(@Param("studentId") Long studentId);

    @Query("SELECT s FROM StudentEntity s WHERE s.email = :email" +
            " AND s.password = :password")
    Optional<StudentEntity> loginStudent(@Param("email") String email,
                                        @Param("password") String password);
    Optional<StudentEntity> findByEmail(String email);
    @Query("SELECT s FROM StudentEntity s WHERE LOWER(s.address) LIKE LOWER(concat('%', :address, ' %'))")
    List<StudentEntity> findByAddressContaining(@Param("address") String address);

}