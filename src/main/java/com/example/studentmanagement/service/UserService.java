package com.example.studentmanagement.service;

import com.example.studentmanagement.model.User;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.name}")
    private String adminUsername;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void createAdminIfNotExists() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User(adminUsername, adminPassword, "ADMIN", adminUsername, "Administrator");
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            userRepository.save(admin);
        }
    }
}