package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Marks;
import com.example.studentmanagement.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }

    public List<Marks> getMarksByStudent(String studentId) {
        return marksRepository.findByStudentId(studentId);
    }

    public List<Marks> getMarksByStudentAndSemester(String studentId, String semester) {
        return marksRepository.findByStudentIdAndSemester(studentId, semester);
    }

    public Marks saveMarks(Marks marks) {
        return marksRepository.save(marks);
    }

    public void deleteMarks(String id) {
        marksRepository.deleteById(id);
    }
}