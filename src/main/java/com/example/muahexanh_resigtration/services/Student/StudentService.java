package com.example.muahexanh_resigtration.services.Student;

import com.example.muahexanh_resigtration.dtos.StudentDTO;
import com.example.muahexanh_resigtration.dtos.UniversityDTO;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import com.example.muahexanh_resigtration.entities.UserEntity;
import com.example.muahexanh_resigtration.repositories.StudentRepository;
import com.example.muahexanh_resigtration.repositories.UniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements iStudentService {
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;
    @Override
    public StudentEntity insertStudent(StudentDTO StudentDTO) throws ParseException {
        Optional<UniversityEntity> university = universityRepository.findById(StudentDTO.getUniversityId());

        StudentEntity newStudent = StudentEntity
                .builder()
                .email(StudentDTO.getEmail())
                .fullName(StudentDTO.getFullName())
                .password(StudentDTO.getPassword())
                .personalDescription(StudentDTO.getPersonalDescription())
                .phoneNumber(StudentDTO.getPhoneNumber())
                .address(StudentDTO.getAddress())
                .role("Student")
                .gender(StudentDTO.getGender())
                .university(university.get())
                .build();

        return studentRepository.save(newStudent);
    }
    @Override
    public StudentEntity getStudentById(long id) throws Exception {
        return null;
    }

    @Override
    public List<StudentEntity> getAllStudent() {
        return null;
    }

    @Override
    public StudentEntity updateStudent(long id, StudentDTO StudentDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteStudent(long id) {

    }
}
