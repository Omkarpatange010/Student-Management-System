package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.User;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import jakarta.annotation.PostConstruct;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @ModelAttribute
    public void addCsrfToken(Model model, HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        if (token != null) {
            model.addAttribute("_csrf", token);
        }
    }

    @PostConstruct
    public void init() {
        userService.createAdminIfNotExists();
    }

    @GetMapping("/")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            String username = auth.getName();
            Optional<User> userOpt = userService.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if ("pending".equals(user.getStatus())) {
                    return "redirect:/pending";
                }
                String role = auth.getAuthorities().iterator().next().getAuthority();
                if ("ROLE_ADMIN".equals(role)) {
                    return "redirect:/admin/dashboard";
                } else if ("ROLE_FACULTY".equals(role)) {
                    return "redirect:/faculty/dashboard";
                } else {
                    return "redirect:/student/dashboard";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String fullName,
                               @RequestParam(required = false) String course,
                               @RequestParam String department,
                               @RequestParam(required = false) Integer year,
                               @RequestParam String phone,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String studentId,
                               @RequestParam String role,
                               Model model) {
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        User user = new User(username, password, role, email, fullName);
        user.setStatus("pending");
        user.setDepartment(department);
        user.setPhone(phone);
        user = userService.saveUser(user);

        if ("FACULTY".equals(role)) {
            // Faculty registration - no additional entity needed
            return "redirect:/login?pending";
        } else {
            // Student registration
            Student student = new Student(user.getId(), studentId != null && !studentId.trim().isEmpty() ? studentId : null, course, department, year != null ? year : 0, phone, address != null ? address : "");
            studentService.saveStudent(student);
            return "redirect:/login?pending";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if ("pending".equals(user.getStatus())) {
                return "redirect:/pending";
            }
            String role = auth.getAuthorities().iterator().next().getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/admin/dashboard";
            } else if ("ROLE_FACULTY".equals(role)) {
                return "redirect:/faculty/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        }
        return "redirect:/login?error";
    }

    @GetMapping("/pending")
    public String pending() {
        return "pending";
    }
}