package com.ConferenceRoomManagement.Utils;

import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	// Email configuration
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "sawantsameer666@gmail.com";
    private static final String SMTP_PASSWORD = "your-app-password";
    private static final String ADMIN_EMAIL = "sawantsameer666@gmail.com";
    
    private void sendConfirmationEmail(String recipientEmail, String roomName, LocalDate bookingDate, String bookedSlots) {
        String subject = "Booking Confirmation";
        String body = String.format("Your booking for %s on %s for slots %s has been confirmed.", roomName, bookingDate, bookedSlots);
        sendEmail(recipientEmail, subject, body);
    }

    private void sendAdminNotificationEmail(String roomName, LocalDate bookingDate, String bookedSlots, String userEmail) {
        String subject = "New Room Booking";
        String body = String.format("A new booking has been made for %s on %s for slots %s by user %s.", roomName, bookingDate, bookedSlots, userEmail);
        sendEmail(ADMIN_EMAIL, subject, body);
    }

	public void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
