package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.ExamSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSettingsRepository extends MongoRepository<ExamSettings, String> {
}