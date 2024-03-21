package com.example.muahexanh_resigtration.services.Student;

import com.example.muahexanh_resigtration.dtos.LoginDTO;
import com.example.muahexanh_resigtration.dtos.StudentDTO;
import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;

import com.example.muahexanh_resigtration.entities.StudentsResigtrationEntity;
import com.example.muahexanh_resigtration.exceptions.DataNotFoundException;
import com.example.muahexanh_resigtration.repositories.*;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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
                    ProjectEntity project = optionalProject.get();


                    // Create a new StudentsResigtrationEntity instance
                    StudentsResigtrationEntity studentsResigtrationEntity = new StudentsResigtrationEntity();

                    // Create the composite key
                    StudentsResigtrationEntityId id = new StudentsResigtrationEntityId();
                    id.setProjectId(project.getId());
                    id.setStudentId(student.getId());

                    // Set the project and student for the registration entity
                    studentsResigtrationEntity.setId(id);
                    studentsResigtrationEntity.setProject(project);
                    studentsResigtrationEntity.setStudent(student);

                    // Set the registration status as needed
                    studentsResigtrationEntity.setRegistration_status("pending");

                    // Save the registration entity
                    studentsResigtrationEntity = studentResigtrationRepository.save(studentsResigtrationEntity);

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


    public List<Map<String, Object>> getAllProjectsOfUniversity(long studentId) throws Exception {
        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        if (studentEntity.isPresent()) {
            StudentEntity student = studentEntity.get();
            String universityNameofStudent = student.getUniversityName();
            Long universityId = universityRepository.getUniversityIdFromName(universityNameofStudent);

            List<ProjectEntity> projects = universityRepository.getAllProjectsOfUniversity(universityId);
            List<Map<String, Object>> projectList = new ArrayList<>();
            if (!projects.isEmpty()) {
                return projects.stream()
                        .map(project -> {
                            Map<String, Object> projectMap = new HashMap<>();
                            projectMap.put("id", project.getId());
                            projectMap.put("title", project.getTitle());
                            projectMap.put("description", project.getDescription());
                            projectMap.put("address", project.getAddress());
                            projectMap.put("maximumStudents", project.getMaxProjectMembers());
                            projectMap.put("maximumSchoolsRegistrationMembers", project.getMaxSchoolRegistrationMembers());
                            projectMap.put("status", project.getStatus());
                            projectMap.put("dateStart", project.getDateStart());
                            projectMap.put("dateEnd", project.getDateEnd());
                            projectMap.put("imgRoot", project.getImgRoot());
                            return projectMap;
                        })
                        .collect(Collectors.toList());
            } else {
                throw new DataNotFoundException("No projects found for university with ID: " + universityId);
            }
        } else {
            throw new Exception("Không tìm thấy sinh viên");
        }
    }


    @Override
    public Map<String, Object> getProjectByStudentIdAndProjectId(long studentId, long projectId) throws Exception {
        if ( studentId == 0 || projectId == 0) {
            // Handle the case where leaderId or projectId is 0
            throw new DataNotFoundException(
                    "Cannot find project with leaderID: " +  studentId + " projectId: " + projectId);
        }

        Optional< ProjectEntity > projectOptional = studentResigtrationRepository.findAllProjectByID(projectId);
        Optional< StudentEntity > studentsOptional = studentRepository.getStudentById(studentId);

        if (projectOptional.isPresent()) {

            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id", projectOptional.get().getId());
            projectMap.put("title", projectOptional.get().getTitle());
            projectMap.put("description", projectOptional.get().getDescription());
            projectMap.put("address", projectOptional.get().getAddress());
            projectMap.put("maxProjectMembers", projectOptional.get().getMaxProjectMembers());
            projectMap.put("maxSchoolsRegistrationMembers", projectOptional.get().getMaxSchoolRegistrationMembers());
            projectMap.put("status", projectOptional.get().getStatus());
            projectMap.put("dateStart", projectOptional.get().getDateStart());
            projectMap.put("dateEnd", projectOptional.get().getDateEnd());
            projectMap.put("students", studentsOptional);
            return projectMap;
        } else {
            throw new DataNotFoundException(
                    "Cannot find project with leaderID: " +  studentId + " projectId: " + projectId + "or student don't belong to this project" );
        }
    }

    @Override
    public   Map<String, Object> getAllProjectOfStudent(Long studentId) throws Exception {
        if ( studentId == 0 ) {
            // Handle the case where leaderId or projectId is 0
            throw new DataNotFoundException(
                    "Cannot find project with leaderID: " +  studentId );
        }

        Optional< List< ProjectEntity > > projectOptional = studentResigtrationRepository. findAllProjectOfStudent(studentId);

        if (projectOptional.isPresent()) {
            List<Map<String, Object>> projectList = new ArrayList<>();
            for (ProjectEntity project : projectOptional.get()) {
                Map<String, Object> projectMap = new HashMap<>();
                projectMap.put("id", project.getId());
                projectMap.put("title", project.getTitle());
                projectMap.put("description", project.getDescription());
                projectMap.put("address", project.getAddress());
                projectMap.put("maximumStudents", project.getMaxProjectMembers());
                projectMap.put("maximumSchoolsRegistrationMembers", project.getMaxSchoolRegistrationMembers());
                projectMap.put("status", project.getStatus());
                projectMap.put("dateStart", project.getDateStart());
                projectMap.put("dateEnd", project.getDateEnd());
                // Add more properties as needed
                projectList.add(projectMap);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("projects", projectList);
            return result;
        } else {
            throw new DataNotFoundException(
                    "Cannot find project with leaderID: " +  studentId + "or student don't belong to this project" );
        }
    }

}
