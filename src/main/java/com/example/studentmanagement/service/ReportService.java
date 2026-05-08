package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.model.Marks;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.User;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    public byte[] generateStudentReport(Student student, User user, List<Marks> marks, List<Attendance> attendance) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font heading = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subHeading = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font body = FontFactory.getFont(FontFactory.HELVETICA, 11);

            document.add(new Paragraph("Academic Report Card", heading));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Name: " + user.getFullName(), body));
            document.add(new Paragraph("Student ID: " + student.getStudentId(), body));
            document.add(new Paragraph("Course: " + student.getCourse(), body));
            document.add(new Paragraph("Department: " + student.getDepartment(), body));
            document.add(new Paragraph("Email: " + user.getEmail(), body));
            document.add(new Paragraph("Phone: " + student.getPhone(), body));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Marks Summary", subHeading));
            PdfPTable marksTable = new PdfPTable(4);
            marksTable.setWidthPercentage(100);
            addTableHeader(marksTable, "Subject ID", "External", "Internal", "Total");
            int totalScore = 0;
            for (Marks mark : marks) {
                marksTable.addCell(new PdfPCell(new Phrase(mark.getSubjectId(), body)));
                marksTable.addCell(new PdfPCell(new Phrase(String.valueOf(mark.getExternalMarks()), body)));
                marksTable.addCell(new PdfPCell(new Phrase(String.valueOf(mark.getInternalMarks()), body)));
                int studentTotal = mark.getExternalMarks() + mark.getInternalMarks();
                totalScore += studentTotal;
                marksTable.addCell(new PdfPCell(new Phrase(String.valueOf(studentTotal), body)));
            }
            document.add(marksTable);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Attendance Summary", subHeading));
            PdfPTable attendanceTable = new PdfPTable(4);
            attendanceTable.setWidthPercentage(100);
            addTableHeader(attendanceTable, "Date", "Status", "Check-In", "Check-Out");
            for (Attendance att : attendance) {
                attendanceTable.addCell(new PdfPCell(new Phrase(att.getDate().toString(), body)));
                attendanceTable.addCell(new PdfPCell(new Phrase(att.getStatus(), body)));
                attendanceTable.addCell(new PdfPCell(new Phrase(att.getCheckInTime() != null ? att.getCheckInTime().toString() : "N/A", body)));
                attendanceTable.addCell(new PdfPCell(new Phrase(att.getCheckOutTime() != null ? att.getCheckOutTime().toString() : "N/A", body)));
            }
            document.add(attendanceTable);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Total Marks: " + totalScore, body));
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException ex) {
            throw new IOException("Unable to generate PDF report", ex);
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
            table.addCell(headerCell);
        }
    }
}
