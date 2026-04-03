package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.User;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
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
        // For simplicity, assume admin is logged in, get current user
        // In real, get from security context
        // Here, hardcode or find admin user
        Optional<User> adminOpt = userService.findByUsername("admin");
        if (adminOpt.isPresent()) {
            User admin = adminOpt.get();
            if (passwordEncoder.matches(oldPassword, admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(admin);
                model.addAttribute("success", "Password changed successfully");
            } else {
                model.addAttribute("error", "Old password is incorrect");
            }
        }
        return "admin/change-password";
    }
}