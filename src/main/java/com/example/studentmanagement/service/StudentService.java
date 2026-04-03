package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findByUserId(String userId) {
        return studentRepository.findByUserId(userId);
    }

    public Optional<Student> findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> findById(String id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
}