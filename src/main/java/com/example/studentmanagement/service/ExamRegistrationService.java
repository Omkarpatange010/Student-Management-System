package com.example.studentmanagement.service;

import com.example.studentmanagement.model.ExamRegistration;
import com.example.studentmanagement.repository.ExamRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamRegistrationService {

    @Autowired
    private ExamRegistrationRepository examRegistrationRepository;

    public ExamRegistration saveExamRegistration(ExamRegistration examRegistration) {
        return examRegistrationRepository.save(examRegistration);
    }

    public List<ExamRegistration> getAllExamRegistrations() {
        return examRegistrationRepository.findAll();
    }

    public List<ExamRegistration> getExamRegistrationsByStudent(String studentId) {
        return examRegistrationRepository.findByStudentId(studentId);
    }

    public List<ExamRegistration> getExamRegistrationsByStatus(String status) {
        return examRegistrationRepository.findByStatus(status);
    }

    public List<ExamRegistration> getExamRegistrationsByUserId(String userId) {
        return examRegistrationRepository.findByUserId(userId);
    }

    public Optional<ExamRegistration> findById(String id) {
        return examRegistrationRepository.findById(id);
    }

    public void updateExamRegistration(ExamRegistration examRegistration) {
        examRegistrationRepository.save(examRegistration);
    }

    public void deleteExamRegistration(String id) {
        examRegistrationRepository.deleteById(id);
    }
}