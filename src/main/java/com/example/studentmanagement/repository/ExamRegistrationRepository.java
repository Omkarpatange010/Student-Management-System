package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.ExamRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRegistrationRepository extends MongoRepository<ExamRegistration, String> {
    List<ExamRegistration> findByStudentId(String studentId);
    List<ExamRegistration> findByStatus(String status);
    List<ExamRegistration> findByUserId(String userId);
}