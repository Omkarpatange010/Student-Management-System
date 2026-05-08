package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByStudent(String studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByStudentAndMonth(String studentId, int year, int month) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .filter(attendance -> {
                    if (attendance.getDate() == null) {
                        return false;
                    }
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(attendance.getDate());
                    return calendar.get(java.util.Calendar.YEAR) == year && calendar.get(java.util.Calendar.MONTH) == month;
                })
                .toList();
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(String id) {
        attendanceRepository.deleteById(id);
    }

    public Optional<Attendance> findById(String id) {
        return attendanceRepository.findById(id);
    }
}