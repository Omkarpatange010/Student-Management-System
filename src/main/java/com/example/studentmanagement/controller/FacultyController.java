package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.*;
import com.example.studentmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExamRegistrationService examRegistrationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Student> students = studentService.findAllStudentsWithUser();
        long totalStudents = students.size();
        long totalAttendanceRecords = attendanceService.getAllAttendance().size();
        long totalMarks = marksService.getAllMarks().size();

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalAttendanceRecords", totalAttendanceRecords);
        model.addAttribute("totalMarks", totalMarks);
        model.addAttribute("students", students);
        return "faculty/dashboard";
    }

    @GetMapping("/id-card")
    public String facultyIdCard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        }
        return "faculty/id-card";
    }

    @PostMapping("/upload-passport")
    public String uploadPassportImage(@RequestParam("passportImage") MultipartFile file, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent() && !file.isEmpty()) {
            User user = userOpt.get();

            try {
                // Create uploads directory if it doesn't exist
                Path uploadDir = Paths.get("uploads/passport");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                // Generate unique filename
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = "passport_" + user.getId() + "_" + System.currentTimeMillis() + extension;

                // Save file
                Path filePath = uploadDir.resolve(filename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Update user with image path
                user.setPassportImage("/uploads/passport/" + filename);
                userService.saveUser(user);

                redirectAttributes.addFlashAttribute("success", "Passport image uploaded successfully!");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to upload image. Please try again.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Please select a valid image file.");
        }

        return "redirect:/faculty/id-card";
    }

    // Student Management
    @GetMapping("/students")
    public String students(Model model) {
        List<Student> students = studentService.findAllStudentsWithUser();
        model.addAttribute("students", students);
        return "faculty/students";
    }

    @GetMapping("/add-student")
    public String addStudent() {
        return "faculty/add-student";
    }

    @PostMapping("/add-student")
    public String addStudent(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String fullName,
                             @RequestParam String studentId,
                             @RequestParam String course,
                             @RequestParam String department,
                             @RequestParam(required = false) Integer year,
                             @RequestParam String phone,
                             @RequestParam String address,
                             RedirectAttributes redirectAttributes) {
        try {
            if (userService.findByUsername(username).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Username already exists");
                return "redirect:/faculty/add-student";
            }
            User user = new User(username, password, "STUDENT", email, fullName);
            user.setDepartment(department);
            user.setPhone(phone);
            user.setStatus("approved");
            userService.saveUser(user);

            Student student = new Student();
            student.setUserId(user.getId());
            student.setStudentId(studentId);
            student.setCourse(course);
            student.setDepartment(department);
            student.setYear(year);
            student.setPhone(phone);
            student.setAddress(address);
            studentService.saveStudent(student);

            redirectAttributes.addFlashAttribute("success", "Student added successfully");
            return "redirect:/faculty/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding student: " + e.getMessage());
            return "redirect:/faculty/add-student";
        }
    }

    @GetMapping("/edit-student/{id}")
    public String editStudent(@PathVariable String id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            Optional<User> userOpt = userService.findById(student.getUserId());
            if (userOpt.isPresent()) {
                model.addAttribute("student", student);
                model.addAttribute("user", userOpt.get());
                return "faculty/edit-student";
            }
        }
        return "redirect:/faculty/students";
    }

    @PostMapping("/update-student")
    public String updateStudent(@RequestParam String id,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String fullName,
                                @RequestParam String studentId,
                                @RequestParam String course,
                                @RequestParam String department,
                                @RequestParam(required = false) Integer year,
                                @RequestParam String phone,
                                @RequestParam String address,
                                RedirectAttributes redirectAttributes) {
        try {
            Optional<Student> studentOpt = studentService.findById(id);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                student.setStudentId(studentId);
                student.setCourse(course);
                student.setDepartment(department);
                student.setYear(year);
                student.setPhone(phone);
                student.setAddress(address);
                studentService.saveStudent(student);

                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setFullName(fullName);
                    user.setDepartment(department);
                    user.setPhone(phone);
                    userService.saveUser(user);
                }

                redirectAttributes.addFlashAttribute("success", "Student updated successfully");
            }
            return "redirect:/faculty/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating student: " + e.getMessage());
            return "redirect:/faculty/edit-student/" + id;
        }
    }

    // Attendance
    @GetMapping("/attendance")
    public String attendance(Model model, @RequestParam(required = false) String studentId) {
        List<Student> students = studentService.findAllStudentsWithUser();
        model.addAttribute("students", students);

        if (studentId != null) {
            List<Attendance> attendances = attendanceService.getAttendanceByStudent(studentId);
            for (Attendance a : attendances) {
                if (a.getStudentId() != null) {
                    studentService.findByIdWithUser(a.getStudentId()).ifPresent(a::setStudent);
                }
            }
            model.addAttribute("attendances", attendances);
            model.addAttribute("selectedStudentId", studentId);
        } else {
            List<Attendance> attendances = attendanceService.getAllAttendance();
            for (Attendance a : attendances) {
                if (a.getStudentId() != null) {
                    studentService.findByIdWithUser(a.getStudentId()).ifPresent(a::setStudent);
                }
            }
            model.addAttribute("attendances", attendances);
        }
        return "faculty/attendance";
    }

    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestParam String studentId,
                                 @RequestParam String attendanceDate,
                                 @RequestParam String status,
                                 RedirectAttributes redirectAttributes) {
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(attendanceDate);
            Attendance attendance = new Attendance(studentId, parsedDate, status);
            studentService.findById(studentId).ifPresent(attendance::setStudent);
            attendanceService.saveAttendance(attendance);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error marking attendance: " + e.getMessage());
        }
        return "redirect:/faculty/attendance";
    }

    @PostMapping("/edit-attendance")
    public String editAttendance(@RequestParam String id,
                                 @RequestParam String status,
                                 RedirectAttributes redirectAttributes) {
        try {
            Optional<Attendance> attendanceOpt = attendanceService.findById(id);
            if (attendanceOpt.isPresent()) {
                Attendance attendance = attendanceOpt.get();
                attendance.setStatus(status);
                attendanceService.saveAttendance(attendance);
                redirectAttributes.addFlashAttribute("success", "Attendance updated successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating attendance: " + e.getMessage());
        }
        return "redirect:/faculty/attendance";
    }

    // Marks
    @GetMapping("/marks")
    public String marks(Model model, @RequestParam(required = false) String studentId) {
        List<Student> students = studentService.findAllStudentsWithUser();
        model.addAttribute("students", students);

        if (studentId != null) {
            List<Marks> marks = marksService.getMarksByStudent(studentId);
            for (Marks m : marks) {
                if (m.getStudentId() != null) {
                    studentService.findByIdWithUser(m.getStudentId()).ifPresent(m::setStudent);
                }
            }
            model.addAttribute("marks", marks);
            model.addAttribute("selectedStudentId", studentId);
        } else {
            List<Marks> marks = marksService.getAllMarks();
            for (Marks m : marks) {
                if (m.getStudentId() != null) {
                    studentService.findByIdWithUser(m.getStudentId()).ifPresent(m::setStudent);
                }
            }
            model.addAttribute("marks", marks);
        }
        return "faculty/marks";
    }

    @PostMapping("/add-marks")
    public String addMarks(@RequestParam String studentId,
                           @RequestParam String subjectId,
                           @RequestParam int externalMarks,
                           @RequestParam int internalMarks,
                           RedirectAttributes redirectAttributes) {
        try {
            Marks marks = new Marks();
            marks.setStudentId(studentId);
            marks.setSubjectId(subjectId);
            marks.setExternalMarks(externalMarks);
            marks.setInternalMarks(internalMarks);
            studentService.findById(studentId).ifPresent(marks::setStudent);
            marksService.saveMarks(marks);
            redirectAttributes.addFlashAttribute("success", "Marks added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding marks: " + e.getMessage());
        }
        return "redirect:/faculty/marks";
    }

    @PostMapping("/update-marks")
    public String updateMarks(@RequestParam String id,
                              @RequestParam int externalMarks,
                              @RequestParam int internalMarks,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Marks> marksOpt = marksService.findById(id);
            if (marksOpt.isPresent()) {
                Marks marks = marksOpt.get();
                marks.setExternalMarks(externalMarks);
                marks.setInternalMarks(internalMarks);
                marksService.saveMarks(marks);
                redirectAttributes.addFlashAttribute("success", "Marks updated successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating marks: " + e.getMessage());
        }
        return "redirect:/faculty/marks";
    }

    // Performance Reports
    @GetMapping("/reports")
    public String reports(Model model) {
        // Similar to admin reports
        List<Student> students = studentService.findAllStudentsWithUser();
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        return "faculty/reports";
    }

    // Exam Registration Management (same as admin)
    @GetMapping("/exam-registrations")
    public String examRegistrations(Model model) {
        List<ExamRegistration> registrations = examRegistrationService.getAllExamRegistrations();
        List<Map<String, Object>> registrationData = new ArrayList<>();
        
        for (ExamRegistration registration : registrations) {
            Map<String, Object> item = new HashMap<>();
            item.put("registration", registration);
            
            // Get student info
            Optional<Student> studentOpt = studentService.findById(registration.getStudentId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                item.put("student", student);
                
                // Get user info
                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    item.put("user", userOpt.get());
                }
            }
            registrationData.add(item);
        }
        
        model.addAttribute("registrationData", registrationData);
        return "faculty/exam-registrations";
    }

    @PostMapping("/approve-exam/{id}")
    public String approveExam(@PathVariable String id) {
        Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(id);
        if (registrationOpt.isPresent()) {
            ExamRegistration registration = registrationOpt.get();
            registration.setStatus("APPROVED");
            registration.setApprovalDate(new java.util.Date());
            
            // Get current faculty user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            registration.setApprovedBy(auth.getName());
            
            examRegistrationService.updateExamRegistration(registration);
        }
        return "redirect:/faculty/exam-registrations";
    }

    @PostMapping("/reject-exam/{id}")
    public String rejectExam(@PathVariable String id, @RequestParam String reason) {
        Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(id);
        if (registrationOpt.isPresent()) {
            ExamRegistration registration = registrationOpt.get();
            registration.setStatus("REJECTED");
            registration.setRejectionReason(reason);
            registration.setApprovalDate(new java.util.Date());
            
            // Get current faculty user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            registration.setApprovedBy(auth.getName());
            
            examRegistrationService.updateExamRegistration(registration);
        }
        return "redirect:/faculty/exam-registrations";
    }
}