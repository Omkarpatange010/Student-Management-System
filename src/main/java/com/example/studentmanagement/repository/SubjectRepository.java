package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    List<Subject> findBySemester(String semester);
}