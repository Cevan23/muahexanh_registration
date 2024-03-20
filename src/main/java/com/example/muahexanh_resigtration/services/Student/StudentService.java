package com.example.muahexanh_resigtration.services.Student;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.StudentDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;

import com.example.muahexanh_resigtration.entities.StudentsResigtrationEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.ProjectRepository;
import com.example.muahexanh_resigtration.repositories.StudentRepository;
import com.example.muahexanh_resigtration.repositories.StudentResigtrationRepository;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
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

    private final StudentResigtrationRepository studentResigtrationRepository;
    private final UniversityRepository universityRepository;


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

        if (newStudent == null) {
            throw new Exception("Cannot create student");
        }

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

        if (studentEntity == null) {
            throw new Exception("Cannot update student");
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
            throw new Exception("Students not found with similar address " + address);
        }
        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("number_of_student", students.size());
        studentMap.put("students", students);
        return studentMap;
    }

    public void applyProject(long studentId, long projectId) throws Exception {
        Optional<StudentEntity> optionalStudent = studentRepository.findById(studentId);
        Optional<Integer> optionalMaxSchoolRegistrationMembers = projectRepository.getmaxSchoolRegistrationMembers(projectId);
        Optional<List<StudentEntity>> optionalStudentsOfProject = studentResigtrationRepository.findAllStudentOfProject(projectId);

        if (optionalStudent.isPresent() && optionalMaxSchoolRegistrationMembers.isPresent() && optionalStudentsOfProject.isPresent()) {
            StudentEntity student = optionalStudent.get();
            Integer maxSchoolRegistrationMembers = optionalMaxSchoolRegistrationMembers.get();
            List<StudentEntity> studentsOfProject = optionalStudentsOfProject.get();

            // Kiểm tra xem sinh viên đã đăng kí cho dự án trước đó hay không
            if (studentsOfProject.contains(student)) {
                throw new Exception("Sinh viên đã đăng ký cho dự án này trước đó.");
            }

            // Đếm số lượng sinh viên của trường trong dự án
            long numberOfStudentsFromSameUniversity = studentsOfProject.stream()
                    .filter(s -> s.getUniversityName().equals(student.getUniversityName()))
                    .count();

            // Kiểm tra xem còn slot đăng kí trống hay không
            if (numberOfStudentsFromSameUniversity < maxSchoolRegistrationMembers) {
                Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);
                if (optionalProject.isPresent()) {
                    StudentsResigtrationEntity project = studentResigtrationRepository.findByProjectsId(projectId);
                    // Thêm sinh viên vào danh sách sinh viên của dự án
                    project.setStudent(student);
                    // Lưu cập nhật vào cơ sở dữ liệu
                    studentResigtrationRepository.save(project);
                } else {
                    throw new Exception("Không tìm thấy dự án với ID đã cung cấp.");
                }
            } else {
                throw new Exception("Số lượng sinh viên đã đạt tối đa cho trường này trong dự án này.");
            }
        } else {
            throw new Exception("Không tìm thấy sinh viên hoặc dự án.");
        }

    }
}
