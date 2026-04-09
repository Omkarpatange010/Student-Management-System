package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Subject> getSubjectsBySemester(String semester) {
        return subjectRepository.findBySemester(semester);
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(String id) {
        subjectRepository.deleteById(id);
    }

    public Optional<Subject> findById(String id) {
        return subjectRepository.findById(id);
    }

    @PostConstruct
    public void initSubjects() {
        if (subjectRepository.count() == 0) {
            // MCA Semester I
            subjectRepository.save(new Subject("Python Programming", "PPR501MJ", 3, 50, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("Data Structure and Algorithms", "DSA502MJ", 3, 50, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("Advanced DBMS", "ADB503MJ", 3, 50, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("Business Statistics", "BST504MJ", 3, 50, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("Software Engineering and Project Management", "SEP505MJ", 3, 50, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("Practical based on Python and DS", "PBP506MJP", 3, 0, 50, "MCA Semester I", "Practical", 350));
            subjectRepository.save(new Subject("Mini Project", "MP541MP", 3, 0, 50, "MCA Semester I", "Practical", 350));
            subjectRepository.save(new Subject("Soft Skills – I", "SSI507MJ", 1, 0, 25, "MCA Semester I", "Theory", 350));
            subjectRepository.save(new Subject("IKS", "IKS508MJ", 1, 0, 25, "MCA Semester I", "Theory", 350));
            // Electives
            subjectRepository.save(new Subject("Fundamentals of Cloud Computing", "FCC510MJ", 3, 50, 25, "MCA Semester I", "Elective", 350));
            subjectRepository.save(new Subject("Web Development", "WDE511MJ", 3, 50, 25, "MCA Semester I", "Elective", 350));
            subjectRepository.save(new Subject("Fundamental of Data Science", "FDS512MJ", 3, 50, 25, "MCA Semester I", "Elective", 350));
            subjectRepository.save(new Subject("Introduction to Cyber Security", "ICE513MJ", 3, 50, 25, "MCA Semester I", "Elective", 350));

            // MCA Semester II
            subjectRepository.save(new Subject("Java Programming", "JPR551MJ", 3, 50, 25, "MCA Semester II", "Theory", 350));
            subjectRepository.save(new Subject("Optimization Techniques", "OTE552MJ", 3, 50, 25, "MCA Semester II", "Theory", 350));
            subjectRepository.save(new Subject("Software Testing and Quality Assurance", "STQ553MJ", 3, 50, 25, "MCA Semester II", "Theory", 350));
            subjectRepository.save(new Subject("Research Methodology", "RMW554MJ", 3, 50, 25, "MCA Semester II", "Theory", 350));
            subjectRepository.save(new Subject("Practical based on Java", "PBJ555MJP", 3, 0, 50, "MCA Semester II", "Practical", 350));
            subjectRepository.save(new Subject("Mini Project", "MP581MP", 3, 0, 50, "MCA Semester II", "Practical", 350));
            // Electives
            subjectRepository.save(new Subject("Cloud Computing Management and Security", "CCM560MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("JavaScript", "JS561MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Machine Learning Techniques", "MLT562MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Essentials of Cyber Security", "ECS563MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Essentials of Cloud Computing and Security", "ECS564MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Advance Web Development", "AWD565MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Power BI", "PBI566MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
            subjectRepository.save(new Subject("Essentials of Information Security", "EIS567MJ", 3, 50, 25, "MCA Semester II", "Elective", 350));
        }
    }

    public void updateAllFeesTo350() {
        List<Subject> subjects = subjectRepository.findAll();
        for (Subject subject : subjects) {
            subject.setFee(350);
            subjectRepository.save(subject);
        }
    }
}