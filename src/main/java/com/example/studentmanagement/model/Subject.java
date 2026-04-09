package com.example.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
public class Subject {

    @Id
    private String id;
    private String courseTitle;
    private String courseCode;
    private int cp; // Credit Points
    private int ext; // External Marks
    private int internal; // Internal Marks
    private String semester; // e.g., "MCA Semester I"
    private String type; // "Theory", "Practical", "Elective"
    private int fee; // Fee per subject

    public Subject() {}

    public Subject(String courseTitle, String courseCode, int cp, int ext, int internal, String semester, String type) {
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.cp = cp;
        this.ext = ext;
        this.internal = internal;
        this.semester = semester;
        this.type = type;
        this.fee = 350; // Default fee for MCA subjects
    }

    public Subject(String courseTitle, String courseCode, int cp, int ext, int internal, String semester, String type, int fee) {
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.cp = cp;
        this.ext = ext;
        this.internal = internal;
        this.semester = semester;
        this.type = type;
        this.fee = fee;
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getExt() {
        return ext;
    }

    public void setExt(int ext) {
        this.ext = ext;
    }

    public int getInternal() {
        return internal;
    }

    public void setInternal(int internal) {
        this.internal = internal;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}