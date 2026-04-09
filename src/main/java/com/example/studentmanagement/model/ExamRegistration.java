package com.example.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "exam_registrations")
public class ExamRegistration {
    @Id
    private String id;
    private String studentId;
    private String userId;
    private String semester;
    private List<String> subjects;
    private int totalFee;
    private String status; // PENDING, APPROVED, REJECTED
    private Date registrationDate;
    private Date approvalDate;
    private String approvedBy;
    private String rejectionReason;

    public ExamRegistration() {}

    public ExamRegistration(String studentId, String userId, String semester, List<String> subjects, int totalFee) {
        this.studentId = studentId;
        this.userId = userId;
        this.semester = semester;
        this.subjects = subjects;
        this.totalFee = totalFee;
        this.status = "PENDING";
        this.registrationDate = new Date();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public int getTotalFee() { return totalFee; }
    public void setTotalFee(int totalFee) { this.totalFee = totalFee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}