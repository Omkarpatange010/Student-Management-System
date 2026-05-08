package com.example.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_settings")
public class ExamSettings {

    @Id
    private String id;
    private boolean examRegistrationEnabled;
    private String adminId; // ID of admin who last modified

    public ExamSettings() {
        this.examRegistrationEnabled = false; // Default to disabled
    }

    public ExamSettings(boolean examRegistrationEnabled, String adminId) {
        this.examRegistrationEnabled = examRegistrationEnabled;
        this.adminId = adminId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExamRegistrationEnabled() {
        return examRegistrationEnabled;
    }

    public void setExamRegistrationEnabled(boolean examRegistrationEnabled) {
        this.examRegistrationEnabled = examRegistrationEnabled;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}