package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.MailBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void sendOrderConfirmationEmail(String toEmail, String transactionCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Xác nhận đơn hàng";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn đã được ghi nhận thành công!</p>"
                + "<p>Mã đơn hàng: " + transactionCode + "</p>"
                + "<p>Vui lòng truy cập <a href='http://localhost:5173/payment-history'>lịch sử thanh toán</a> để xem chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailToCustomer(String toEmail, int consignmentID) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Xác nhận đơn hàng";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Đơn ký gửi của bạn đã được duyệt thành công!</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Vui lòng truy cập <a href='http://localhost:5173/payment-history'>lịch sử thanh toán</a> để xem chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

}
