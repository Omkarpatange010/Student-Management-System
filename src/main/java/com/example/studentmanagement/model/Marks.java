package com.example.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marks")
public class Marks {

    @Id
    private String id;
    private String studentId; // reference to Student id
    private String subjectId; // reference to Subject id
    private int externalMarks;
    private int internalMarks;
    private String semester;

    public Marks() {}

    public Marks(String studentId, String subjectId, int externalMarks, int internalMarks, String semester) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.externalMarks = externalMarks;
        this.internalMarks = internalMarks;
        this.semester = semester;
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(int externalMarks) {
        this.externalMarks = externalMarks;
    }

    public int getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(int internalMarks) {
        this.internalMarks = internalMarks;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}