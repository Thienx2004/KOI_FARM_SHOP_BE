package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.MailBody;
import com.group2.KoiFarmShop.dto.request.ConsignmentKoiCare;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${APP_DOMAIN}")
    private String domain;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendSimpleMess(MailBody mailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.getTo());
        message.setFrom("locttse182329@fpt.edu.vn");
        message.setSubject(mailBody.getSubject());
        message.setText(mailBody.getText());

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
                + "<p>Vui lòng truy cập <a href='"+domain+"/payment-history'>lịch sử thanh toán</a> để xem chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailApproveToCustomer(String toEmail, int consignmentID) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Thông báo đơn ký gửi";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Đơn ký gửi của bạn đã được duyệt thành công!</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Vui lòng truy cập <a href='"+domain+"/payment-history'>lịch sử thanh toán</a> để xem chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailForCareFish(String toEmail, int consignmentID, ConsignmentKoiCare consignmentKoiCare) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String note="Không có ghi chú";
        if(consignmentKoiCare.getNote() != null&&!consignmentKoiCare.getNote().isEmpty()) {
             note = consignmentKoiCare.getNote();
        }
        String subject = "Thông báo tình trạng cá ký gửi";
        String content = "<p>Xin chào,</p>"
                + "<p>Chúng tôi xin gửi tới bạn cập nhật mới nhất về tình trạng của cá Koi mà bạn đã ký gửi chăm sóc tại trại của chúng tôi.</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Tình trạng cá Koi hiện tại:</p>"
                + "<ul>"
                + "<li>Sức khỏe: " + consignmentKoiCare.getHealthStatus() + "</li>"
                + "<li>Tăng trưởng: " + consignmentKoiCare.getGrowthStatus() + "</li>"
                + "<li>Môi trường chăm sóc: " + consignmentKoiCare.getCareEnvironment() + "</li>"
                + "<li>Ghi chú: " + note + "</li>"
                + "</ul>"
                + "<p>Chúng tôi sẽ tiếp tục theo dõi và cập nhật tình trạng sức khỏe của cá Koi cho bạn.</p>"
                + "<p>Vui lòng truy cập <a href='"+domain+"/koi-care-status'>tình trạng chăm sóc</a> để xem chi tiết và theo dõi thường xuyên.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailRejectToCustomer(String toEmail, int consignmentID, String rejectReason) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Thông báo đơn ký gửi";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Chúng tôi rất tiếc phải từ chốt đơn ký gửi của bạn!</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Lý do từ chối đơn ký gửi: " + rejectReason + "</p>"
                + "<p>Mọi thắc mắc xin liên hệ để biết thêm chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailCheckKoiToCustomer(String toEmail, int consignmentID) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Thông báo đơn ký gửi";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Đơn ký gửi của bạn đã được chúng tôi xem xét và mong muốn được kiểm tra trực tiếp Koi của bạn.</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Chúng tôi sẽ sớm liên hệ với bạn qua thông tin bạn đã cung cấp.</p>"
                + "<p>Mọi thắc mắc xin liên hệ để biết thêm chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

    public void sendEmailSaleConsignKoiToCustomer(String toEmail, int consignmentID, String emailCustomer) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Thông báo đơn ký gửi";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã tin tưởng dùng dịch vụ của chúng tôi. Koi ký gửi của bạn đã được mua bởi tài khoản: " + emailCustomer + " .</p>"
                + "<p>Mã đơn ký gửi: " + consignmentID + "</p>"
                + "<p>Chúng tôi sẽ chuyển tiền cho bạn qua tài khoản ngân hàng từ 3 đến 5 ngày sau.</p>"
                + "<p>Mọi thắc mắc xin liên hệ để biết thêm chi tiết.</p>"
                + "<p>Trân trọng!</p>";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

}
