package com.example.studentmanagement.service;

import com.example.studentmanagement.model.ExamSettings;
import com.example.studentmanagement.repository.ExamSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamSettingsService {

    @Autowired
    private ExamSettingsRepository examSettingsRepository;

    public ExamSettings getExamSettings() {
        return examSettingsRepository.findAll().stream().findFirst().orElse(new ExamSettings());
    }

    public ExamSettings saveExamSettings(ExamSettings examSettings) {
        // Ensure only one settings document exists
        examSettingsRepository.deleteAll();
        return examSettingsRepository.save(examSettings);
    }

    public boolean isExamRegistrationEnabled() {
        ExamSettings settings = getExamSettings();
        return settings.isExamRegistrationEnabled();
    }

    public void setExamRegistrationEnabled(boolean enabled, String adminId) {
        ExamSettings settings = getExamSettings();
        settings.setExamRegistrationEnabled(enabled);
        settings.setAdminId(adminId);
        saveExamSettings(settings);
    }
}