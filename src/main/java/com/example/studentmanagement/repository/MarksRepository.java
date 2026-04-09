package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Marks;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MarksRepository extends MongoRepository<Marks, String> {
    List<Marks> findByStudentId(String studentId);
    List<Marks> findBySubjectId(String subjectId);
    List<Marks> findByStudentIdAndSemester(String studentId, String semester);
}