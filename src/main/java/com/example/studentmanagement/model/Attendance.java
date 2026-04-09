package com.example.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;
    private String studentId; // reference to Student id
    private Date date;
    private String status; // "Present", "Absent"

    public Attendance() {}

    public Attendance(String studentId, Date date, String status) {
        this.studentId = studentId;
        this.date = date;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}