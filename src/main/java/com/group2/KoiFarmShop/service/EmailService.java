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

    public void sendVerificationEmail(String email, String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String subject = "Mã xác nhận OTP";
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"vi\">\n" +
                " <head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "  <title>New Message</title>\n" +
                "  <!--[if (mso 16)]>\n" +
                "    <style type=\"text/css\">\n" +
                "    a {text-decoration: none;}\n" +
                "    </style>\n" +
                "    <![endif]-->\n" +
                "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n" +
                "  <!--[if gte mso 9]>\n" +
                "<noscript>\n" +
                "         <xml>\n" +
                "           <o:OfficeDocumentSettings>\n" +
                "           <o:AllowPNG></o:AllowPNG>\n" +
                "           <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "           </o:OfficeDocumentSettings>\n" +
                "         </xml>\n" +
                "      </noscript>\n" +
                "<![endif]-->\n" +
                "\n" +
                " </head>\n" +
                " <body class=\"body\" style=\"width:100%;height:100%;padding:0;Margin:0\">\n" +
                "  <div style=\"display: flex;justify-content: center; width: 100%;\" dir=\"ltr\" class=\"es-wrapper-color\" lang=\"vi\" style=\"background-color:#FAFAFA\">\n" +
                "\n" +
                "   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"es-wrapper\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FAFAFA\">\n" +
                "    <tbody>\n" +
                "     <tr>\n" +
                "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-content\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "        <tbody>\n" +
                "         <tr>\n" +
                "          <td align=\"center\" class=\"es-info-area\" style=\"padding:0;Margin:0\">\n" +
                "           <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#00000000\" class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" role=\"none\">\n" +
                "            <tbody>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                <tbody>\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                    <tbody>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                     </tr>\n" +
                "                    </tbody>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "                </tbody>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "            </tbody>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "        </tbody>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-header\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "        <tbody>\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#ffffff\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"es-header-body\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\n" +
                "            <tbody>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;background-color:#ffffff\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                <tbody>\n" +
                "                 <tr>\n" +
                "                  <td valign=\"top\" align=\"center\" class=\"es-m-p0r\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                    <tbody>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px;font-size:0px\"><img src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\" alt=\"Logo\" width=\"200\" title=\"Logo\" style=\"display:block;font-size:12px;border:0;outline:none;text-decoration:none\"></td>\n" +
                "                     </tr>\n" +
                "                    </tbody>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "                </tbody>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "            </tbody>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "        </tbody>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-content\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "        <tbody>\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table bgcolor=\"#ffffff\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"es-content-body\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "            <tbody>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-right:20px;padding-left:20px;padding-top:30px;padding-bottom:30px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                <tbody>\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                    <tbody>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h1 class=\"es-m-txt-c\" style=\"text-align:center; Margin:0;font-family:arial, 'helvetica neue', helvetica, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:46px;font-style:normal;font-weight:bold;line-height:46px;color:#333333\">Xác Nhận Email</h1></td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" class=\"es-m-p0r es-m-p0l\" style=\"Margin:0;padding-top:5px;padding-right:40px;padding-bottom:5px;padding-left:40px\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;letter-spacing:0;color:#333333;font-size:14px\">\"Bạn đã nhận được tin nhắn này vì địa chỉ email của bạn đã được đăng ký với trang web của chúng tôi. Vui lòng nhập mã OTP dưới đây để xác minh địa chỉ email của bạn và xác nhận rằng bạn là chủ sở hữu của tài khoản này</p></td>\n" +
                "                     </tr>\n" +
                "                   \n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#2CB543;background:#5C68E2;border-width:0px;display:inline-block;border-radius:6px;width:auto\"><a href=\"\" target=\"_blank\" class=\"es-button\" style=\"mso-style-priority:100 !important;text-decoration:none !important;mso-line-height-rule:exactly;color:#FFFFFF;font-size:20px;padding:10px 30px 10px 30px;display:inline-block;background:#5C68E2;border-radius:6px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:normal;font-style:normal;line-height:24px;width:auto;text-align:center;letter-spacing:0;mso-padding-alt:0;mso-border-alt:10px solid #5C68E2;padding-left:30px;padding-right:30px\">"+otp+"</a></span></td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" class=\"es-m-p0r es-m-p0l\" style=\"Margin:0;padding-top:5px;padding-right:40px;padding-bottom:5px;padding-left:40px\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;letter-spacing:0;color:#333333;font-size:14px\">Sau khi xác nhận, email này sẽ được liên kết duy nhất với tài khoản của bạn.</p></td>\n" +
                "                     </tr>\n" +
                "                    </tbody>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "                </tbody>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "            </tbody>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "        </tbody>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-footer\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "        <tbody>\n" +
                "         <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\" role=\"none\">\n" +
                "            <tbody>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-right:20px;padding-left:20px;padding-bottom:20px;padding-top:20px\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                <tbody>\n" +
                "                 <tr>\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                    <tbody>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\">\n" +
                "                       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                        <tbody>\n" +
                "                         <tr>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><a href=\"https://www.facebook.com/profile.php?id=61568109532999\"><img title=\"Facebook\" src=\"https://frrkldx.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png\" alt=\"Fb\" width=\"32\" style=\"display:block;font-size:14px;border:0;outline:none;text-decoration:none\"></a></td>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"X\" src=\"https://frrkldx.stripocdn.email/content/assets/img/social-icons/logo-black/x-logo-black.png\" alt=\"X\" width=\"32\" style=\"display:block;font-size:14px;border:0;outline:none;text-decoration:none\"></td>\n" +
                "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:40px\"><img title=\"Instagram\" src=\"https://frrkldx.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png\" alt=\"Inst\" width=\"32\" style=\"display:block;font-size:14px;border:0;outline:none;text-decoration:none\"></td>\n" +
                "                         </tr>\n" +
                "                        </tbody>\n" +
                "                       </table></td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:35px\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;letter-spacing:0;color:#333333;font-size:12px\">© 2024 Cửa hàng cá koi Koi Farm Shop. Tất cả các quyền được bảo lưu.<br></p></td>\n" +
                "                     </tr>\n" +
                "                    </tbody>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "                </tbody>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "            </tbody>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "        </tbody>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-content\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n" +
                "        <tbody>\n" +
                "         <tr>\n" +
                "          <td align=\"center\" class=\"es-info-area\" style=\"padding:0;Margin:0\">\n" +
                "           <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#00000000\" class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" role=\"none\">\n" +
                "            <tbody>\n" +
                "             <tr>\n" +
                "              <td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                <tbody>\n" +
                "                 <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n" +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                    <tbody>\n" +
                "                     <tr>\n" +
                "                      <td align=\"center\" class=\"es-infoblock\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;letter-spacing:0;color:#CCCCCC;font-size:12px\"><a target=\"_blank\" href=\"\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a>No longer want to receive these emails?&nbsp;<a href=\"\" target=\"_blank\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\">Unsubscribe</a>.<a target=\"_blank\" href=\"\" style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"></a></p></td>\n" +
                "                     </tr>\n" +
                "                    </tbody>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "                </tbody>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "            </tbody>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "        </tbody>\n" +
                "       </table></td>\n" +
                "     </tr>\n" +
                "    </tbody>\n" +
                "   </table>\n" +
                "  </div>\n" +
                " </body>\n" +
                "</html>";

        // Gửi email với OTP

//        SimpleMailMessage emailMessage = new SimpleMailMessage();
        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
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
