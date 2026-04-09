package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.User;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.model.Marks;
import com.example.studentmanagement.model.ExamRegistration;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.SubjectService;
import com.example.studentmanagement.service.AttendanceService;
import com.example.studentmanagement.service.MarksService;
import com.example.studentmanagement.service.ExamRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private ExamRegistrationService examRegistrationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Student> students = studentService.findAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Attendance> attendances = attendanceService.getAllAttendance();
        List<Marks> marks = marksService.getAllMarks();
        List<ExamRegistration> examRegistrations = examRegistrationService.getAllExamRegistrations();
        long pendingCount = examRegistrations.stream()
            .filter(reg -> reg != null && "PENDING".equals(reg.getStatus()))
            .count();
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        model.addAttribute("attendances", attendances);
        model.addAttribute("marks", marks);
        model.addAttribute("examRegistrations", examRegistrations);
        model.addAttribute("pendingApprovals", pendingCount);
        return "admin/dashboard";
    }

    @GetMapping("/students")
    public String students(Model model) {
        try {
            List<Student> students = studentService.findAllStudents();
            if (students == null) {
                students = new ArrayList<>();
            }
            List<Map<String, Object>> studentsData = new ArrayList<>();
            for (Student student : students) {
                if (student == null || student.getId() == null) {
                    continue;
                }
                Map<String, Object> item = new HashMap<>();
                item.put("student", student);
                
                // Safely get user info
                String userId = student.getUserId();
                if (userId != null && !userId.trim().isEmpty()) {
                    userService.findById(userId).ifPresent(user -> {
                        if (user != null) {
                            item.put("name", user.getFullName() != null ? user.getFullName() : "N/A");
                            item.put("email", user.getEmail() != null ? user.getEmail() : "N/A");
                        }
                    });
                }
                if (!item.containsKey("name")) {
                    item.put("name", "N/A");
                    item.put("email", "N/A");
                }
                studentsData.add(item);
            }
            model.addAttribute("studentsData", studentsData);
            return "admin/students";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading students: " + e.getMessage());
            return "admin/students";
        }
    }

    @GetMapping("/add-student")
    public String addStudent() {
        return "admin/add-student";
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
                             Model model) {
        try {
            if (userService.findByUsername(username).isPresent()) {
                model.addAttribute("error", "Username already exists");
                return "admin/add-student";
            }
            if (studentService.findByStudentId(studentId).isPresent()) {
                model.addAttribute("error", "Student ID already exists");
                return "admin/add-student";
            }
            if (year == null) {
                year = 1;
            }
            User user = new User(username, password, "STUDENT", email, fullName);
            user = userService.saveUser(user);
            if (user == null || user.getId() == null) {
                model.addAttribute("error", "Failed to save user.");
                return "admin/add-student";
            }
            Student student = new Student(user.getId(), studentId, course, department, year, phone, address);
            studentService.saveStudent(student);
            return "redirect:/admin/students?added";
        } catch (Exception ex) {
            model.addAttribute("error", "Unable to add student: " + ex.getMessage());
            return "admin/add-student";
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
                return "admin/edit-student";
            }
        }
        return "redirect:/admin/students";
    }

    @PostMapping("/edit-student/{id}")
    public String editStudent(@PathVariable String id,
                              @RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String fullName,
                              @RequestParam String studentId,
                              @RequestParam String course,
                              @RequestParam String department,
                              @RequestParam int year,
                              @RequestParam String phone,
                              @RequestParam String address) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setStudentId(studentId);
            student.setCourse(course);
            student.setDepartment(department);
            student.setYear(year);
            student.setPhone(phone);
            student.setAddress(address);
            studentService.updateStudent(student);

            Optional<User> userOpt = userService.findById(student.getUserId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setUsername(username);
                user.setEmail(email);
                user.setFullName(fullName);
                userService.updateUser(user);
            }
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable String id) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            studentService.deleteStudent(id);
            userService.deleteUser(student.getUserId());
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "admin/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(user);
                model.addAttribute("success", "Password changed successfully");
            } else {
                model.addAttribute("error", "Old password is incorrect");
            }
        } else {
            model.addAttribute("error", "User not found");
        }
        return "admin/change-password";
    }

    // Subjects management
    @GetMapping("/subjects")
    public String subjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    @GetMapping("/add-subject")
    public String addSubject() {
        return "admin/add-subject";
    }

    @PostMapping("/add-subject")
    public String addSubject(@RequestParam String courseTitle,
                             @RequestParam String courseCode,
                             @RequestParam int cp,
                             @RequestParam int ext,
                             @RequestParam int internal,
                             @RequestParam String semester,
                             @RequestParam String type) {
        Subject subject = new Subject(courseTitle, courseCode, cp, ext, internal, semester, type);
        subjectService.saveSubject(subject);
        return "redirect:/admin/subjects";
    }

    @PostMapping("/update-fees")
    public String updateFees() {
        subjectService.updateAllFeesTo350();
        return "redirect:/admin/subjects";
    }

    // Attendance management
    @GetMapping("/attendance")
    public String attendance(Model model, @RequestParam(required = false) String studentId) {
        List<Student> students = studentService.findAllStudents();
        List<Map<String, Object>> studentData = new ArrayList<>();
        for (Student student : students) {
            Map<String, Object> item = new HashMap<>();
            item.put("student", student);
            Optional<User> userOpt = userService.findById(student.getUserId());
            if (userOpt.isPresent()) {
                item.put("name", userOpt.get().getFullName());
            } else {
                item.put("name", "N/A");
            }
            studentData.add(item);
        }
        List<Attendance> attendances = attendanceService.getAllAttendance();
        List<Map<String, Object>> attendanceData = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (studentId != null && !attendance.getStudentId().equals(studentId)) continue;
            Map<String, Object> item = new HashMap<>();
            item.put("attendance", attendance);
            Optional<Student> studentOpt = studentService.findById(attendance.getStudentId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                item.put("studentId", student.getStudentId());
                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    item.put("name", userOpt.get().getFullName());
                } else {
                    item.put("name", "N/A");
                }
            } else {
                item.put("studentId", "N/A");
                item.put("name", "N/A");
            }
            attendanceData.add(item);
        }
        model.addAttribute("studentData", studentData);
        model.addAttribute("attendanceData", attendanceData);
        if (studentId != null) {
            Optional<Student> studentOpt = studentService.findById(studentId);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                model.addAttribute("filterStudent", student);
                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    model.addAttribute("filterStudentName", userOpt.get().getFullName());
                }
            }
        }
        return "admin/attendance";
    }

    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestParam String studentId,
                                 @RequestParam String date,
                                 @RequestParam String status) throws Exception {
        java.util.Date attendanceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(date);
        Attendance attendance = new Attendance(studentId, attendanceDate, status);
        attendanceService.saveAttendance(attendance);
        return "redirect:/admin/attendance";
    }

    // Marks management
    @GetMapping("/marks")
    public String marks(Model model, @RequestParam(required = false) String studentId) {
        List<Student> students = studentService.findAllStudents();
        List<Map<String, Object>> studentData = new ArrayList<>();
        for (Student student : students) {
            Map<String, Object> item = new HashMap<>();
            item.put("student", student);
            Optional<User> userOpt = userService.findById(student.getUserId());
            if (userOpt.isPresent()) {
                item.put("name", userOpt.get().getFullName());
            } else {
                item.put("name", "N/A");
            }
            studentData.add(item);
        }
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Marks> marks = marksService.getAllMarks();
        List<Map<String, Object>> marksData = new ArrayList<>();
        for (Marks mark : marks) {
            if (studentId != null && !mark.getStudentId().equals(studentId)) continue;
            Map<String, Object> item = new HashMap<>();
            item.put("marks", mark);
            Optional<Student> studentOpt = studentService.findById(mark.getStudentId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                item.put("studentId", student.getStudentId());
                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    item.put("name", userOpt.get().getFullName());
                } else {
                    item.put("name", "N/A");
                }
            } else {
                item.put("studentId", "N/A");
                item.put("name", "N/A");
            }
            Optional<Subject> subjectOpt = subjects.stream().filter(s -> s.getId().equals(mark.getSubjectId())).findFirst();
            if (subjectOpt.isPresent()) {
                item.put("subjectTitle", subjectOpt.get().getCourseTitle());
            } else {
                item.put("subjectTitle", "N/A");
            }
            marksData.add(item);
        }
        model.addAttribute("studentData", studentData);
        model.addAttribute("subjects", subjects);
        model.addAttribute("marksData", marksData);
        if (studentId != null) {
            Optional<Student> studentOpt = studentService.findById(studentId);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                model.addAttribute("filterStudent", student);
                Optional<User> userOpt = userService.findById(student.getUserId());
                if (userOpt.isPresent()) {
                    model.addAttribute("filterStudentName", userOpt.get().getFullName());
                }
            }
        }
        return "admin/marks";
    }

    @GetMapping("/pending-registrations")
    public String pendingRegistrations(Model model) {
        List<User> pendingUsers = userService.findAllUsers().stream()
                .filter(user -> "pending".equals(user.getStatus()) && "STUDENT".equals(user.getRole()))
                .collect(java.util.stream.Collectors.toList());
        List<Map<String, Object>> pendingData = new ArrayList<>();
        for (User user : pendingUsers) {
            Map<String, Object> item = new HashMap<>();
            item.put("user", user);
            Optional<Student> studentOpt = studentService.findByUserId(user.getId());
            if (studentOpt.isPresent()) {
                item.put("student", studentOpt.get());
            }
            pendingData.add(item);
        }
        model.addAttribute("pendingData", pendingData);
        return "admin/pending-registrations";
    }

    @PostMapping("/approve-registration")
    public String approveRegistration(@RequestParam String userId) {
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setStatus("approved");
            // Generate student ID
            String studentId = generateStudentId();
            userService.updateUser(user);
            Optional<Student> studentOpt = studentService.findByUserId(userId);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                student.setStudentId(studentId);
                studentService.updateStudent(student);
            }
        }
        return "redirect:/admin/pending-registrations";
    }

    private String generateStudentId() {
        List<Student> students = studentService.findAllStudents();
        int maxId = 5100; // Start from MC25101, so 5101
        for (Student s : students) {
            if (s.getStudentId() != null && s.getStudentId().startsWith("MC25")) {
                try {
                    int id = Integer.parseInt(s.getStudentId().substring(4));
                    if (id > maxId) maxId = id;
                } catch (Exception e) {}
            }
        }
        return "MC25" + String.format("%03d", maxId + 1);
    }

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
        return "admin/exam-registrations";
    }

    @PostMapping("/approve-exam/{id}")
    public String approveExam(@PathVariable String id) {
        Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(id);
        if (registrationOpt.isPresent()) {
            ExamRegistration registration = registrationOpt.get();
            registration.setStatus("APPROVED");
            registration.setApprovalDate(new java.util.Date());
            
            // Get current admin user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            registration.setApprovedBy(auth.getName());
            
            examRegistrationService.updateExamRegistration(registration);
        }
        return "redirect:/admin/exam-registrations";
    }

    @PostMapping("/reject-exam/{id}")
    public String rejectExam(@PathVariable String id, @RequestParam String reason) {
        Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(id);
        if (registrationOpt.isPresent()) {
            ExamRegistration registration = registrationOpt.get();
            registration.setStatus("REJECTED");
            registration.setRejectionReason(reason);
            registration.setApprovalDate(new java.util.Date());
            
            // Get current admin user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            registration.setApprovedBy(auth.getName());
            
            examRegistrationService.updateExamRegistration(registration);
        }
        return "redirect:/admin/exam-registrations";
    }
}