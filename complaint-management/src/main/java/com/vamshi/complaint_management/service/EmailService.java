package com.vamshi.complaint_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendComplaintRaisedEmail(String to, String userName,
                                         Long complaintId, String title) {
        String subject = "Complaint #" + complaintId + " Raised Successfully";
        String body = "Dear " + userName + ",\n\n"
                + "Your complaint has been raised successfully.\n\n"
                + "Complaint ID: #" + complaintId + "\n"
                + "Title: " + title + "\n"
                + "Status: OPEN\n\n"
                + "We will assign an agent to look into your issue shortly.\n\n"
                + "Regards,\n"
                + "Complaint Management Team";
        sendEmail(to, subject, body);
    }

    public void sendStatusUpdateEmail(String to, String userName,
                                      Long complaintId, String title,
                                      String status, String resolutionNotes) {
        String subject = "Complaint #" + complaintId + " Status Updated to " + status;
        String body = "Dear " + userName + ",\n\n"
                + "Your complaint status has been updated.\n\n"
                + "Complaint ID: #" + complaintId + "\n"
                + "Title: " + title + "\n"
                + "Status: " + status + "\n"
                + (resolutionNotes != null ? "Resolution Notes: " + resolutionNotes + "\n" : "")
                + "\nRegards,\n"
                + "Complaint Management Team";
        sendEmail(to, subject, body);
    }

    public void sendComplaintAssignedEmail(String to, String agentName,
                                           Long complaintId, String title) {
        String subject = "Complaint #" + complaintId + " Assigned to You";
        String body = "Dear " + agentName + ",\n\n"
                + "A complaint has been assigned to you.\n\n"
                + "Complaint ID: #" + complaintId + "\n"
                + "Title: " + title + "\n"
                + "Please look into this issue and update the status.\n\n"
                + "Regards,\n"
                + "Complaint Management Team";
        sendEmail(to, subject, body);
    }
}
