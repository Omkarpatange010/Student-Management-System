package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.User;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

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
}