package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendSimpleMess(MailBody mailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("locttse182329@fpt.edu.vn");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        javaMailSender.send(message);
    }

    public void sendVerificationEmail(String email, String otp) {
        String subject = "Mã xác nhận OTP";
        String message = "Mã OTP của bạn là: " + otp + ". OTP sẽ hết hạn trong vòng 2 phút.";

        // Gửi email với OTP

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("koifarmofficial@gmail.com");
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        javaMailSender.send(emailMessage);
    }

}
