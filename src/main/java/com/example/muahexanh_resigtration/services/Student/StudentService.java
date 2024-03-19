package com.example.muahexanh_resigtration.services.Student;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.StudentDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import com.example.muahexanh_resigtration.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements iStudentService {
    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public StudentEntity insertStudent(StudentDTO StudentDTO) throws Exception {
        Optional<StudentEntity> existingStudent = studentRepository.findByEmail(StudentDTO.getEmail());
        if (existingStudent.isPresent()) {
            throw new Exception("Email already exists");
        }

        StudentEntity newStudent = StudentEntity
                .builder()
                .email(StudentDTO.getEmail())
                .fullName(StudentDTO.getFullName())
                .password(passwordEncoder.encode(StudentDTO.getPassword()))
                .personalDescription(StudentDTO.getPersonalDescription())
                .phoneNumber(StudentDTO.getPhoneNumber())
                .address(StudentDTO.getAddress())
                .role("Student")
                .isMale(StudentDTO.getIsMale())
                .universityName(String.valueOf(StudentDTO.getUniversityName()))
                .build();

        return studentRepository.save(newStudent);
    }

    @Override
    public StudentEntity loginStudent(LoginDTO loginDTO) throws Exception {
        Optional<StudentEntity> student = studentRepository.findByEmail(loginDTO.getEmail());
        if (student.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), student.get().getPassword())) {
            throw new DataNotFoundException("Invalid email or password");
        }
        return student.get();
    }

    @Override
    public StudentEntity updateStudent(long id, StudentDTO StudentDTO) throws Exception {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new DataNotFoundException("Cannot find student with id = " + id);
        }
        StudentEntity studentEntity = student.get();

        if (StudentDTO.getEmail() != null) {
            studentEntity.setEmail(StudentDTO.getEmail());
        }
        if (StudentDTO.getFullName() != null) {
            studentEntity.setFullName(StudentDTO.getFullName());
        }
        if (StudentDTO.getPassword() != null) {
            studentEntity.setPassword(StudentDTO.getPassword());
        }
        if (StudentDTO.getPersonalDescription() != null) {
            studentEntity.setPersonalDescription(StudentDTO.getPersonalDescription());
        }
        if (StudentDTO.getPhoneNumber() != null) {
            studentEntity.setPhoneNumber(StudentDTO.getPhoneNumber());
        }
        if (StudentDTO.getAddress() != null) {
            studentEntity.setAddress(StudentDTO.getAddress());
        }
        if (StudentDTO.getIsMale() != null) {
            studentEntity.setIsMale(StudentDTO.getIsMale());
        }
        if (StudentDTO.getUniversityName() != null) {
            studentEntity.setUniversityName(String.valueOf(StudentDTO.getUniversityName()));
        }
        return studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudent(long id) throws ParseException {

    }

    @Override
    public StudentEntity getStudentById(long id) throws Exception {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new Exception("Student not found");
        }
        return student.get();
    }

    @Override
    public List<StudentEntity> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Map<String, Object> getAllStudentContainAddress(String address) throws Exception {
        List<StudentEntity> students = studentRepository.findByAddressContaining(address);
        if (students.isEmpty()) {
            throw new Exception("Students not found with similar address "+address);
        }
        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("number_of_student", students.size());
        studentMap.put("students", students);
        return studentMap;
    }
}
