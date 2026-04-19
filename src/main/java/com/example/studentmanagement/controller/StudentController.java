package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.User;
import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.model.Marks;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.model.ExamRegistration;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.service.AttendanceService;
import com.example.studentmanagement.service.MarksService;
import com.example.studentmanagement.service.SubjectService;
import com.example.studentmanagement.service.ExamRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.io.IOException;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamRegistrationService examRegistrationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
            }
        }
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
                
                // Get all subjects for exam registration form
                List<Subject> semester1Subjects = subjectService.getSubjectsBySemester("MCA Semester I");
                List<Subject> semester2Subjects = subjectService.getSubjectsBySemester("MCA Semester II");
                model.addAttribute("semester1Subjects", semester1Subjects);
                model.addAttribute("semester2Subjects", semester2Subjects);
            }
        }
        return "student/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String email,
                                @RequestParam String fullName,
                                @RequestParam String phone,
                                @RequestParam String address) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(email);
            user.setFullName(fullName);
            userService.updateUser(user);

            Optional<Student> studentOpt = studentService.findByUserId(user.getId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                student.setPhone(phone);
                student.setAddress(address);
                studentService.updateStudent(student);
            }
        }
        return "redirect:/student/profile";
    }

    @GetMapping("/attendance")
    public String attendance(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                List<Attendance> attendances = attendanceService.getAttendanceByStudent(studentOpt.get().getId());
                model.addAttribute("attendances", attendances);
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
            }
        }
        return "student/attendance";
    }

    @GetMapping("/marks")
    public String marks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                List<Marks> marks = marksService.getMarksByStudent(studentOpt.get().getId());
                List<Subject> subjects = subjectService.getAllSubjects();
                model.addAttribute("marks", marks);
                model.addAttribute("subjects", subjects);
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
            }
        }
        return "student/marks";
    }

    @GetMapping("/subjects")
    public String subjects(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                List<Subject> subjects = subjectService.getAllSubjects();
                model.addAttribute("subjects", subjects);
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
            }
        }
        return "student/subjects";
    }

    @GetMapping("/id-card")
    public String idCard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
            }
        }
        return "student/id-card";
    }

    @PostMapping("/add-subject")
    public String addSubject(@RequestParam String courseTitle,
                             @RequestParam String courseCode,
                             @RequestParam int cp,
                             @RequestParam int ext,
                             @RequestParam int internal,
                             @RequestParam String semester,
                             @RequestParam String type,
                             @RequestParam(defaultValue = "350") int fee) {
        Subject subject = new Subject(courseTitle, courseCode, cp, ext, internal, semester, type, fee);
        subjectService.saveSubject(subject);
        return "redirect:/student/subjects";
    }

    @PostMapping("/register-exam")
    public String registerExam(@RequestParam String semester,
                               @RequestParam(value = "subjects", required = false) String[] subjects,
                               Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent() && subjects != null && subjects.length > 0) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                // Calculate fee based on actual subject fees
                int totalFee = 0;
                List<Subject> selectedSubjectsList = new ArrayList<>();
                
                for (String subjectId : subjects) {
                    Optional<Subject> subjectOpt = subjectService.findById(subjectId);
                    if (subjectOpt.isPresent()) {
                        Subject subj = subjectOpt.get();
                        totalFee += subj.getFee();
                        selectedSubjectsList.add(subj);
                    } else {
                        totalFee += 350; // Use default if subject not found
                    }
                }
                
                // Create exam registration
                ExamRegistration examRegistration = new ExamRegistration(
                    studentOpt.get().getId(),
                    userOpt.get().getId(),
                    semester,
                    java.util.Arrays.asList(subjects),
                    totalFee
                );
                
                // Save to database
                examRegistrationService.saveExamRegistration(examRegistration);
                
                // Store exam registration data in model for confirmation
                model.addAttribute("semester", semester);
                model.addAttribute("selectedSubjects", selectedSubjectsList);
                model.addAttribute("subjectCount", subjects.length);
                model.addAttribute("totalFee", totalFee);
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
                model.addAttribute("registrationId", examRegistration.getId());
                
                return "student/exam-pending-approval";
            }
        }
        
        // If validation fails, redirect back to profile
        return "redirect:/student/profile";
    }

    @GetMapping("/exam-status")
    public String examStatus(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                List<ExamRegistration> registrations = examRegistrationService.getExamRegistrationsByUserId(userOpt.get().getId());
                model.addAttribute("registrations", registrations);
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
                return "student/exam-status";
            }
        }
        return "redirect:/student/dashboard";
    }

    @GetMapping("/proceed-to-payment/{registrationId}")
    public String proceedToPayment(@PathVariable String registrationId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent()) {
            Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(registrationId);
            if (registrationOpt.isPresent()) {
                ExamRegistration registration = registrationOpt.get();
                
                // Check if the registration belongs to the current user and is approved
                if (registration.getUserId().equals(userOpt.get().getId()) && "APPROVED".equals(registration.getStatus())) {
                    Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
                    if (studentOpt.isPresent()) {
                        model.addAttribute("semester", registration.getSemester());
                        model.addAttribute("selectedSubjects", registration.getSubjects().toArray(new String[0]));
                        model.addAttribute("subjectCount", registration.getSubjects().size());
                        model.addAttribute("feePerSubject", 350);
                        model.addAttribute("totalFee", registration.getTotalFee());
                        model.addAttribute("student", studentOpt.get());
                        model.addAttribute("user", userOpt.get());
                        model.addAttribute("registrationId", registrationId);
                        
                        return "student/payment-gateway";
                    }
                }
            }
        }
        return "redirect:/student/exam-status";
    }

    @PostMapping("/process-payment")
    public String processPayment(@RequestParam String paymentMethod,
                                 @RequestParam String semester,
                                 @RequestParam String subjects,
                                 @RequestParam int totalFee,
                                 @RequestParam(required = false) String registrationId,
                                 Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent()) {
            Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
            if (studentOpt.isPresent()) {
                // Here you would typically integrate with a real payment gateway
                // For demo purposes, we'll simulate a successful payment
                
                // If there's a registration ID, update its status
                if (registrationId != null && !registrationId.isEmpty()) {
                    Optional<ExamRegistration> registrationOpt = examRegistrationService.findById(registrationId);
                    if (registrationOpt.isPresent()) {
                        ExamRegistration registration = registrationOpt.get();
                        registration.setStatus("COMPLETED");
                        examRegistrationService.updateExamRegistration(registration);
                    }
                }
                
                // Store payment details in model for confirmation
                model.addAttribute("paymentMethod", paymentMethod);
                model.addAttribute("semester", semester);
                model.addAttribute("subjects", subjects.split(","));
                model.addAttribute("totalFee", totalFee);
                model.addAttribute("transactionId", "TXN" + System.currentTimeMillis());
                model.addAttribute("paymentDate", new java.util.Date());
                model.addAttribute("student", studentOpt.get());
                model.addAttribute("user", userOpt.get());
                
                return "student/payment-success";
            }
        }
        
        return "redirect:/student/profile";
    }

    @PostMapping("/upload-photo")
    public String uploadPhoto(@RequestParam("profilePhoto") MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent() && !file.isEmpty()) {
            try {
                // Convert file to Base64
                String base64Photo = Base64.getEncoder().encodeToString(file.getBytes());
                
                Optional<Student> studentOpt = studentService.findByUserId(userOpt.get().getId());
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    student.setProfilePhoto("data:" + file.getContentType() + ";base64," + base64Photo);
                    studentService.updateStudent(student);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/student/dashboard";
    }
}