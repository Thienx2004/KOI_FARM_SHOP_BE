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
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html\n" +
                "  dir=\"ltr\"\n" +
                "  xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "  lang=\"vi\"\n" +
                ">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>New Message</title>\n" +
                "    <!--[if (mso 16)]>\n" +
                "      <style type=\"text/css\">\n" +
                "        a {\n" +
                "          text-decoration: none;\n" +
                "        }\n" +
                "      </style>\n" +
                "    <![endif]-->\n" +
                "    <!--[if gte mso 9\n" +
                "      ]><style>\n" +
                "        sup {\n" +
                "          font-size: 100% !important;\n" +
                "        }\n" +
                "      </style><!\n" +
                "    [endif]-->\n" +
                "    <!--[if gte mso 9]>\n" +
                "      <noscript>\n" +
                "        <xml>\n" +
                "          <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG></o:AllowPNG>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "          </o:OfficeDocumentSettings>\n" +
                "        </xml>\n" +
                "      </noscript>\n" +
                "    <![endif]-->\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f4f4f4;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background: linear-gradient(135deg, #ffffff, #f0f0ff);\n" +
                "        padding: 20px;\n" +
                "        border-radius: 8px;\n" +
                "        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "      \"\n" +
                "    >\n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellpadding=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        style=\"border-collapse: collapse\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td align=\"center\" style=\"padding: 20px\">\n" +
                "            <img\n" +
                "              src=\"https://frrkldx.stripocdn.email/content/guids/CABINET_18d164c898baeeedd825b332f76ee4d53cbe4a0b1c3f023f63423459afda813c/images/logo.png\"\n" +
                "              alt=\"Logo\"\n" +
                "              style=\"width: 150px; margin-bottom: 20px\"\n" +
                "            />\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td\n" +
                "            style=\"\n" +
                "              padding: 20px;\n" +
                "              text-align: center;\n" +
                "              border-bottom: 1px solid #ddd;\n" +
                "            \"\n" +
                "          >\n" +
                "            <h1\n" +
                "              style=\"\n" +
                "                font-family: Arial, sans-serif;\n" +
                "                font-size: 28px;\n" +
                "                color: #333;\n" +
                "                margin: 0 0 10px;\n" +
                "              \"\n" +
                "            >\n" +
                "              Xác Nhận Đơn Hàng\n" +
                "            </h1>\n" +
                "            <p\n" +
                "              style=\"\n" +
                "                font-family: Arial, sans-serif;\n" +
                "                font-size: 16px;\n" +
                "                color: #555;\n" +
                "                margin: 0;\n" +
                "              \"\n" +
                "            >\n" +
                "              Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn đã được ghi nhận thành\n" +
                "              công!\n" +
                "            </p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 20px; text-align: center\">\n" +
                "            <p\n" +
                "              style=\"\n" +
                "                font-family: Arial, sans-serif;\n" +
                "                font-size: 16px;\n" +
                "                color: #555;\n" +
                "                margin: 0;\n" +
                "                background-color: #f9f9f9;\n" +
                "                padding: 10px;\n" +
                "                border-radius: 4px;\n" +
                "                display: inline-block;\n" +
                "              \"\n" +
                "            >\n" +
                "              Mã đơn hàng: <strong style=\"color: #333\"> "+transactionCode+" </strong>\n" +
                "            </p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td align=\"center\" style=\"padding: 20px\">\n" +
                "            <a\n" +
                "              href=\"https://koi-farm-shop-fe.vercel.app/payment-history\"\n" +
                "              style=\"\n" +
                "                display: inline-block;\n" +
                "                padding: 10px 20px;\n" +
                "                font-family: Arial, sans-serif;\n" +
                "                font-size: 16px;\n" +
                "                color: #ffffff;\n" +
                "                background-color: #5c68e2;\n" +
                "                text-decoration: none;\n" +
                "                border-radius: 4px;\n" +
                "                transition: background-color 0.3s;\n" +
                "              \"\n" +
                "              onmouseover=\"this.style.backgroundColor='#3b49d3';\"\n" +
                "              onmouseout=\"this.style.backgroundColor='#5c68e2';\"\n" +
                "              >Xem Lịch Sử Thanh Toán</a\n" +
                "            >\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td\n" +
                "            style=\"\n" +
                "              padding: 20px;\n" +
                "              font-family: Arial, sans-serif;\n" +
                "              font-size: 12px;\n" +
                "              color: #999;\n" +
                "              text-align: center;\n" +
                "            \"\n" +
                "          >\n" +
                "            <p style=\"margin: 0\">Trân trọng!</p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

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
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html\n" +
                "  dir=\"ltr\"\n" +
                "  xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "  lang=\"vi\"\n" +
                ">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>New Message</title>\n" +
                "    <!--[if (mso 16)]>\n" +
                "      <style type=\"text/css\">\n" +
                "        a {\n" +
                "          text-decoration: none;\n" +
                "        }\n" +
                "      </style>\n" +
                "    <![endif]-->\n" +
                "    <!--[if gte mso 9\n" +
                "      ]><style>\n" +
                "        sup {\n" +
                "          font-size: 100% !important;\n" +
                "        }\n" +
                "      </style><!\n" +
                "    [endif]-->\n" +
                "    <!--[if gte mso 9]>\n" +
                "      <noscript>\n" +
                "        <xml>\n" +
                "          <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG></o:AllowPNG>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "          </o:OfficeDocumentSettings>\n" +
                "        </xml>\n" +
                "      </noscript>\n" +
                "    <![endif]-->\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f4f8fb;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      color: #333;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        border-radius: 12px;\n" +
                "        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "   \n" +
                "      <div style=\"text-align: center\">\n" +
                "        <img\n" +
                "          src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\"\n" +
                "          alt=\"Koi Farm Logo\"\n" +
                "          style=\"width: 150px; margin-bottom: 20px\"\n" +
                "        />\n" +
                "        <h1\n" +
                "          style=\"\n" +
                "            background-color: #f0faff;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            text-align: center;\n" +
                "            margin: 20px 0;\n" +
                "            border: 1px solid #cde8f6;\n" +
                "            color: #5d4037;\n" +
                "            font-size: 24px;\n" +
                "          \"\n" +
                "        >\n" +
                "          Thông Báo Đơn Ký Gửi\n" +
                "        </h1>\n" +
                "      </div>\n" +
                "\n" +
                "     \n" +
                "      <div style=\"padding: 20px; line-height: 1.6\">\n" +
                "        <p style=\"margin: 0\">Xin chào,</p>\n" +
                "        <p style=\"margin: 15px 0\">\n" +
                "          Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Đơn ký\n" +
                "          gửi của bạn đã được duyệt thành công!\n" +
                "        </p>\n" +
                "        <div\n" +
                "          style=\"\n" +
                "            background-color: #f0faff;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            text-align: center;\n" +
                "            margin: 20px 0;\n" +
                "            border: 1px solid #cde8f6;\n" +
                "          \"\n" +
                "        >\n" +
                "          <p style=\"margin: 0; font-size: 16px\">Mã đơn ký gửi:</p>\n" +
                "          <p\n" +
                "            style=\"\n" +
                "              margin: 0;\n" +
                "              font-size: 24px;\n" +
                "              font-weight: bold;\n" +
                "              color: #004085;\n" +
                "            \"\n" +
                "          >\n" +
                "            "+consignmentID+"\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <p>\n" +
                "          Vui lòng truy cập\n" +
                "          <a\n" +
                "            href=\"https://koi-farm-shop-fe.vercel.app/consignment-history\"\n" +
                "            style=\"\n" +
                "              color: #d84315;\n" +
                "              text-decoration: none;\n" +
                "              border-bottom: 1px solid #d84315;\n" +
                "            \"\n" +
                "            >lịch sử thanh toán</a\n" +
                "          >\n" +
                "          để xem chi tiết.\n" +
                "        </p>\n" +
                "        <p style=\"margin-top: 20px\">Trân trọng,</p>\n" +
                "        <p><strong>Đội ngũ Koi Farm</strong></p>\n" +
                "      </div>\n" +
                "\n" +
                "     \n" +
                "      <div\n" +
                "        style=\"text-align: center; padding: 10px; font-size: 12px; color: #666\"\n" +
                "      >\n" +
                "        <p style=\"margin: 0\">Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>\n" +
                "       \n" +
                "        <div style=\"margin-top: 10px\">\n" +
                "          <a\n" +
                "            href=\"https://www.facebook.com/profile.php?id=61568109532999\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/facebook.png?alt=media&token=992e5fef-6942-4474-900a-3eb46a21b840\"\n" +
                "              alt=\"Facebook\"\n" +
                "              style=\"width: 25px\"\n" +
                "              title=\"facebook icons\"\n" +
                "          /></a>\n" +
                "          <a\n" +
                "            href=\"https://instagram.com/yourpage\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/instagram.png?alt=media&token=6046da46-2e6a-4f73-9385-88fc7ed5da95\"\n" +
                "              alt=\"Instagram\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "          <a href=\"https://twitter.com/yourpage\" style=\"text-decoration: none\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/twitter.png?alt=media&token=114256dc-d2a0-4e2a-902e-57a9e833dd4f\"\n" +
                "              alt=\"Twitter\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

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
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html\n" +
                "  dir=\"ltr\"\n" +
                "  xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "  lang=\"vi\"\n" +
                ">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>New Message</title>\n" +
                "    <!--[if (mso 16)]>\n" +
                "      <style type=\"text/css\">\n" +
                "        a {\n" +
                "          text-decoration: none;\n" +
                "        }\n" +
                "      </style>\n" +
                "    <![endif]-->\n" +
                "    <!--[if gte mso 9\n" +
                "      ]><style>\n" +
                "        sup {\n" +
                "          font-size: 100% !important;\n" +
                "        }\n" +
                "      </style><!\n" +
                "    [endif]-->\n" +
                "    <!--[if gte mso 9]>\n" +
                "      <noscript>\n" +
                "        <xml>\n" +
                "          <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG></o:AllowPNG>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "          </o:OfficeDocumentSettings>\n" +
                "        </xml>\n" +
                "      </noscript>\n" +
                "    <![endif]-->\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f5f7fa;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      color: #333;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        border-radius: 12px;\n" +
                "        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        position: relative;\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "   \n" +
                "      <div\n" +
                "        style=\"\n" +
                "          position: absolute;\n" +
                "          top: 0;\n" +
                "          left: 0;\n" +
                "          width: 100%;\n" +
                "          height: 100%;\n" +
                "          background-image: url('https://as1.ftcdn.net/v2/jpg/08/04/91/60/1000_F_804916030_7NMGzxioysyGgNqf2DdgxO0AThRtNKf4.webp');\n" +
                "          background-size: contain;\n" +
                "          background-repeat: no-repeat;\n" +
                "          background-position: center;\n" +
                "          opacity: 0.15;\n" +
                "          pointer-events: none;\n" +
                "        \"\n" +
                "      ></div>\n" +
                "\n" +
                "    \n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellpadding=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        style=\"border-collapse: collapse; position: relative; z-index: 1\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td align=\"center\" style=\"padding: 20px\">\n" +
                "            <img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\"\n" +
                "              alt=\"Koi Farm Logo\"\n" +
                "              style=\"width: 120px; margin-bottom: 15px\"\n" +
                "            />\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td\n" +
                "            align=\"center\"\n" +
                "          \n" +
                "          >\n" +
                "            <h2\n" +
                "              style=\"\n" +
                "                background-color: #f0faff;\n" +
                "                padding: 15px;\n" +
                "                border-radius: 8px;\n" +
                "                text-align: center;\n" +
                "                margin: 20px 0;\n" +
                "                border: 1px solid #cde8f6;\n" +
                "              \"\n" +
                "            >\n" +
                "              \uD83D\uDC1F Thông Báo Tình Trạng Cá Ký Gửi \uD83D\uDC1F\n" +
                "            </h>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "\n" +
                "    \n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellpadding=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        style=\"\n" +
                "          border-collapse: collapse;\n" +
                "          margin-top: 20px;\n" +
                "          position: relative;\n" +
                "          z-index: 1;\n" +
                "        \"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 15px; line-height: 1.6; color: #333\">\n" +
                "            <p style=\"margin: 0\">Xin chào,</p>\n" +
                "            <p style=\"margin: 15px 0\">\n" +
                "              Chúng tôi xin gửi tới bạn cập nhật mới nhất về tình trạng của cá\n" +
                "              Koi mà bạn đã ký gửi chăm sóc tại trại của chúng tôi.\n" +
                "            </p>\n" +
                "            <div\n" +
                "              style=\"\n" +
                "                background-color: #f0faff;\n" +
                "                padding: 15px;\n" +
                "                border-radius: 8px;\n" +
                "                text-align: center;\n" +
                "                margin: 20px 0;\n" +
                "                border: 1px solid #cde8f6;\n" +
                "              \"\n" +
                "            >\n" +
                "              <p style=\"margin: 0; font-size: 16px\">Mã đơn ký gửi:</p>\n" +
                "              <p\n" +
                "                style=\"\n" +
                "                  margin: 0;\n" +
                "                  font-size: 24px;\n" +
                "                  font-weight: bold;\n" +
                "                  color: #004085;\n" +
                "                \"\n" +
                "              >\n" +
                "                "+consignmentID+"\n" +
                "              </p>\n" +
                "            </div>\n" +
                "            <p style=\"margin: 10px 0\">\n" +
                "              <strong>Tình trạng cá Koi hiện tại:</strong>\n" +
                "            </p>\n" +
                "            <ul style=\"padding-left: 20px; margin: 10px 0\">\n" +
                "              <li style=\"margin: 5px 0\">\n" +
                "                Sức khỏe: <strong style=\"color: #bf360c\">"+consignmentKoiCare.getHealthStatus()+"</strong>\n" +
                "              </li>\n" +
                "              <li style=\"margin: 5px 0\">\n" +
                "                Tăng trưởng: <strong style=\"color: #bf360c\">"+consignmentKoiCare.getGrowthStatus()+"</strong>\n" +
                "              </li>\n" +
                "              <li style=\"margin: 5px 0\">\n" +
                "                Môi trường chăm sóc:\n" +
                "                <strong style=\"color: #bf360c\"\n" +
                "                  >"+consignmentKoiCare.getCareEnvironment()+"</strong\n" +
                "                >\n" +
                "              </li>\n" +
                "              <li style=\"margin: 5px 0\">\n" +
                "                Ghi chú:\n" +
                "                <em>"+consignmentKoiCare.getNote()+"</em>\n" +
                "              </li>\n" +
                "            </ul>\n" +
                "            <p style=\"margin: 10px 0\">\n" +
                "              Chúng tôi sẽ tiếp tục theo dõi và cập nhật tình trạng sức khỏe của\n" +
                "              cá Koi cho bạn.\n" +
                "            </p>\n" +
                "            <p style=\"margin: 10px 0\">\n" +
                "              Vui lòng truy cập\n" +
                "              <a\n" +
                "                href=\"https://koi-farm-shop-fe.vercel.app/my-consignment-koi\"\n" +
                "                style=\"\n" +
                "                  color: #d84315;\n" +
                "                  text-decoration: none;\n" +
                "                  border-bottom: 1px solid #d84315;\n" +
                "                \"\n" +
                "                >tình trạng chăm sóc</a\n" +
                "              >\n" +
                "              để xem chi tiết và theo dõi thường xuyên.\n" +
                "            </p>\n" +
                "            <p style=\"margin: 20px 0 0\">Trân trọng,</p>\n" +
                "            <p style=\"margin: 0\"><strong>Đội ngũ Koi Farm</strong></p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "\n" +
                "      \n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellpadding=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        style=\"\n" +
                "          border-collapse: collapse;\n" +
                "          margin-top: 20px;\n" +
                "          position: relative;\n" +
                "          z-index: 1;\n" +
                "        \"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td align=\"center\">\n" +
                "            <hr\n" +
                "              style=\"border: none; border-top: 2px dotted #ccc; margin: 20px 0\"\n" +
                "            />\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "\n" +
                "     \n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellpadding=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        style=\"border-collapse: collapse; position: relative; z-index: 1\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td\n" +
                "            style=\"\n" +
                "              padding: 10px;\n" +
                "              text-align: center;\n" +
                "              font-size: 12px;\n" +
                "              color: #666;\n" +
                "            \"\n" +
                "          >\n" +
                "            <p style=\"margin: 0\">\n" +
                "              Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của Koi Farm!\n" +
                "            </p>\n" +
                "            <!-- Social Media Icons -->\n" +
                "            <div style=\"margin-top: 10px\">\n" +
                "              <a\n" +
                "              \n" +
                "                href=\"https://www.facebook.com/profile.php?id=61568109532999\"\n" +
                "                style=\"text-decoration: none;; margin-right: 10px\"\n" +
                "                ><img\n" +
                "                  src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/facebook.png?alt=media&token=992e5fef-6942-4474-900a-3eb46a21b840\" alt=\"Facebook\" style=\"width: 25px;\" title=\"facebook icons\" \n" +
                "                  \n" +
                "                  \n" +
                "              /></a>\n" +
                "              <a\n" +
                "                href=\"https://instagram.com/yourpage\"\n" +
                "                style=\"text-decoration: none; margin-right: 10px\"\n" +
                "                ><img\n" +
                "                  src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/instagram.png?alt=media&token=6046da46-2e6a-4f73-9385-88fc7ed5da95\"\n" +
                "                  alt=\"Instagram\"\n" +
                "                  style=\"width: 24px\"\n" +
                "              /></a>\n" +
                "              <a\n" +
                "                href=\"https://twitter.com/yourpage\"\n" +
                "                style=\"text-decoration: none\"\n" +
                "                ><img\n" +
                "                  src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/twitter.png?alt=media&token=114256dc-d2a0-4e2a-902e-57a9e833dd4f\"\n" +
                "                  alt=\"Twitter\"\n" +
                "                  style=\"width: 24px\"\n" +
                "              /></a>\n" +
                "            </div>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

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
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <title>Thông Báo Đơn Ký Gửi</title>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f8f9fa;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      color: #333;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        border-radius: 12px;\n" +
                "        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "      \"\n" +
                "    >\n" +
                "    \n" +
                "      <div style=\"text-align: center; margin-bottom: 20px\">\n" +
                "        <img\n" +
                "          src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\"\n" +
                "          alt=\"Koi Farm Logo\"\n" +
                "          style=\"width: 150px; margin-bottom: 15px\"\n" +
                "        />\n" +
                "        <h1\n" +
                "          style=\"\n" +
                "            background-color: #f8d7da;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            color: #721c24;\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "          \"\n" +
                "        >\n" +
                "          Thông Báo Từ Chối Đơn Ký Gửi\n" +
                "        </h1>\n" +
                "      </div>\n" +
                "\n" +
                "    \n" +
                "      <div style=\"padding: 20px; line-height: 1.6\">\n" +
                "        <p>Xin chào,</p>\n" +
                "        <p>\n" +
                "          Chúng tôi rất tiếc phải thông báo rằng đơn ký gửi Koi của bạn đã không\n" +
                "          được chấp nhận. Chúng tôi xin chân thành cảm ơn bạn đã quan tâm và tin\n" +
                "          tưởng dịch vụ của chúng tôi.\n" +
                "        </p>\n" +
                "        <div\n" +
                "          style=\"\n" +
                "            background-color: #fff3cd;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            margin: 20px 0;\n" +
                "            border-left: 4px solid #ffeeba;\n" +
                "          \"\n" +
                "        >\n" +
                "          <p style=\"margin: 0; font-size: 16px; color: #856404\">\n" +
                "            <strong>Mã đơn ký gửi:</strong>" + consignmentID + "\n" +
                "          </p>\n" +
                "          <p style=\"margin: 10px 0 0; font-size: 16px; color: #856404\">\n" +
                "            <strong>Lý do từ chối:</strong> " + rejectReason + "\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <p>\n" +
                "          Mọi thắc mắc xin vui lòng liên hệ với chúng tôi để được giải đáp chi\n" +
                "          tiết.\n" +
                "        </p>\n" +
                "        <p style=\"margin-top: 20px\">Trân trọng,</p>\n" +
                "        <p><strong>Đội ngũ Koi Farm</strong></p>\n" +
                "      </div>\n" +
                "\n" +
                "     \n" +
                "      <div\n" +
                "        style=\"text-align: center; padding: 10px; font-size: 12px; color: #666\"\n" +
                "      >\n" +
                "        <p style=\"margin: 0\">\n" +
                "          Cảm ơn bạn đã quan tâm và tin tưởng dịch vụ của chúng tôi!\n" +
                "        </p>\n" +
                "       \n" +
                "        <div style=\"margin-top: 10px\">\n" +
                "          <a\n" +
                "            href=\"https://www.facebook.com/profile.php?id=61568109532999\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/facebook.png?alt=media&token=992e5fef-6942-4474-900a-3eb46a21b840\"\n" +
                "              alt=\"Facebook\"\n" +
                "              style=\"width: 25px\"\n" +
                "              title=\"facebook icons\"\n" +
                "          /></a>\n" +
                "          <a\n" +
                "            href=\"https://instagram.com/yourpage\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/instagram.png?alt=media&token=6046da46-2e6a-4f73-9385-88fc7ed5da95\"\n" +
                "              alt=\"Instagram\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "          <a href=\"https://twitter.com/yourpage\" style=\"text-decoration: none\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/twitter.png?alt=media&token=114256dc-d2a0-4e2a-902e-57a9e833dd4f\"\n" +
                "              alt=\"Twitter\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

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
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <title>Thông Báo Đơn Ký Gửi</title>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f4f8fb;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      color: #333;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        border-radius: 12px;\n" +
                "        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "      \n" +
                "      <div style=\"text-align: center; margin-bottom: 20px\">\n" +
                "        <img\n" +
                "          src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\"\n" +
                "          alt=\"Koi Farm Logo\"\n" +
                "          style=\"width: 150px; margin-bottom: 15px\"\n" +
                "        />\n" +
                "        <h1\n" +
                "          style=\"\n" +
                "            background-color: #e0f7fa;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            color: #006064;\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "          \"\n" +
                "        >\n" +
                "          Thông Báo Đơn Ký Gửi\n" +
                "        </h1>\n" +
                "      </div>\n" +
                "\n" +
                "    \n" +
                "      <div style=\"padding: 20px; line-height: 1.6\">\n" +
                "        <p>Xin chào,</p>\n" +
                "        <p>\n" +
                "          Cảm ơn bạn đã tin tưởng dùng dịch vụ ký gửi Koi của chúng tôi. Đơn ký\n" +
                "          gửi của bạn đã được chúng tôi xem xét và mong muốn được kiểm tra trực\n" +
                "          tiếp Koi của bạn.\n" +
                "        </p>\n" +
                "        <div\n" +
                "          style=\"\n" +
                "            background-color: #f0faff;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            margin: 20px 0;\n" +
                "            border-left: 4px solid #b2ebf2;\n" +
                "          \"\n" +
                "        >\n" +
                "          <p style=\"margin: 0; font-size: 16px; color: #004d40\">\n" +
                "            <strong>Mã đơn ký gửi:</strong> " + consignmentID + "\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <p>Chúng tôi sẽ sớm liên hệ với bạn qua thông tin bạn đã cung cấp.</p>\n" +
                "        <p>Mọi thắc mắc xin vui lòng liên hệ để biết thêm chi tiết.</p>\n" +
                "        <p style=\"margin-top: 20px\">Trân trọng,</p>\n" +
                "        <p><strong>Đội ngũ Koi Farm</strong></p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div\n" +
                "        style=\"text-align: center; padding: 10px; font-size: 12px; color: #666\"\n" +
                "      >\n" +
                "        <p style=\"margin: 0\">Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>\n" +
                "\n" +
                "        <div style=\"margin-top: 10px\">\n" +
                "          <a\n" +
                "            href=\"https://www.facebook.com/profile.php?id=61568109532999\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/facebook.png?alt=media&token=992e5fef-6942-4474-900a-3eb46a21b840\"\n" +
                "              alt=\"Facebook\"\n" +
                "              style=\"width: 25px\"\n" +
                "              title=\"facebook icons\"\n" +
                "          /></a>\n" +
                "          <a\n" +
                "            href=\"https://instagram.com/yourpage\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/instagram.png?alt=media&token=6046da46-2e6a-4f73-9385-88fc7ed5da95\"\n" +
                "              alt=\"Instagram\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "          <a href=\"https://twitter.com/yourpage\" style=\"text-decoration: none\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/twitter.png?alt=media&token=114256dc-d2a0-4e2a-902e-57a9e833dd4f\"\n" +
                "              alt=\"Twitter\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

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
        String content = "<p>Mọi thắc mắc <!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <title>Thông Báo Đơn Ký Gửi</title>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      background-color: #f4f8fb;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      color: #333;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: auto;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        border-radius: 12px;\n" +
                "        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "    \n" +
                "      <div style=\"text-align: center; margin-bottom: 20px\">\n" +
                "        <img\n" +
                "          src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/Logo%2Flogo.png?alt=media&token=0dc23cde-c8b5-4256-8e33-d604279c78c4\"\n" +
                "          alt=\"Koi Farm Logo\"\n" +
                "          style=\"width: 150px; margin-bottom: 15px\"\n" +
                "        />\n" +
                "        <h1\n" +
                "          style=\"\n" +
                "            background-color: #d1e7dd;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            color: #155724;\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "          \"\n" +
                "        >\n" +
                "          Thông Báo Giao Dịch Thành Công\n" +
                "        </h1>\n" +
                "      </div>\n" +
                "\n" +
                "   \n" +
                "      <div style=\"padding: 20px; line-height: 1.6\">\n" +
                "        <p>Xin chào,</p>\n" +
                "        <p>\n" +
                "          Cảm ơn bạn đã tin tưởng dùng dịch vụ của chúng tôi. Cá Koi ký gửi của\n" +
                "          bạn đã được mua bởi tài khoản: <strong>" + emailCustomer +" </strong>.\n" +
                "        </p>\n" +
                "        <div\n" +
                "          style=\"\n" +
                "            background-color: #e9f7ef;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            margin: 20px 0;\n" +
                "            border-left: 4px solid #c3e6cb;\n" +
                "          \"\n" +
                "        >\n" +
                "          <p style=\"margin: 0; font-size: 16px; color: #155724\">\n" +
                "            <strong>Mã đơn ký gửi:</strong> " + consignmentID + "\n" +
                "          </p>\n" +
                "        </div>\n" +
                "        <p>\n" +
                "          Chúng tôi sẽ chuyển tiền cho bạn qua tài khoản ngân hàng từ 3 đến 5\n" +
                "          ngày sau.\n" +
                "        </p>\n" +
                "        <p>Mọi thắc mắc xin vui lòng liên hệ để biết thêm chi tiết.</p>\n" +
                "        <p style=\"margin-top: 20px\">Trân trọng,</p>\n" +
                "        <p><strong>Đội ngũ Koi Farm</strong></p>\n" +
                "      </div>\n" +
                "\n" +
                "     \n" +
                "      <div\n" +
                "        style=\"text-align: center; padding: 10px; font-size: 12px; color: #666\"\n" +
                "      >\n" +
                "        <p style=\"margin: 0\">Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>\n" +
                "       \n" +
                "        <div style=\"margin-top: 10px\">\n" +
                "          <a\n" +
                "            href=\"https://www.facebook.com/profile.php?id=61568109532999\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/facebook.png?alt=media&token=992e5fef-6942-4474-900a-3eb46a21b840\"\n" +
                "              alt=\"Facebook\"\n" +
                "              style=\"width: 25px\"\n" +
                "              title=\"facebook icons\"\n" +
                "          /></a>\n" +
                "          <a\n" +
                "            href=\"https://instagram.com/yourpage\"\n" +
                "            style=\"text-decoration: none; margin-right: 10px\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/instagram.png?alt=media&token=6046da46-2e6a-4f73-9385-88fc7ed5da95\"\n" +
                "              alt=\"Instagram\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "          <a href=\"https://twitter.com/yourpage\" style=\"text-decoration: none\"\n" +
                "            ><img\n" +
                "              src=\"https://firebasestorage.googleapis.com/v0/b/koi-shop-3290e.appspot.com/o/twitter.png?alt=media&token=114256dc-d2a0-4e2a-902e-57a9e833dd4f\"\n" +
                "              alt=\"Twitter\"\n" +
                "              style=\"width: 24px\"\n" +
                "          /></a>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        helper.setFrom("koifarmofficial@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);  // Nội dung email dạng HTML

        javaMailSender.send(message);
    }

}
