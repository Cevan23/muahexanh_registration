package com.example.muahexanh_resigtration.responses.Student;

import com.example.muahexanh_resigtration.entities.StudentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class StudentListResponse {

    private List<StudentResponse> students;

    public static StudentListResponse fromListStudent(List<StudentEntity> students) {
        List<StudentResponse> studentResponses = students.stream()
                .map(StudentResponse::fromStudent)
                .toList();

        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.setStudents(studentResponses);
        return studentListResponse;
    }

    private void setStudents(List<StudentResponse> studentResponses) {
        this.students = studentResponses;
    }

    public List<StudentResponse> getStudents() {
        return this.students;
    }
}