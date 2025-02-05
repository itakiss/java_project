package hr.java.project.glavna.api;


import hr.java.project.ProjectApplication;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static hr.java.project.data.UserData.getEmailFromUserId;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {

    public static void sendEmail(String subject, String body, String filePath) {
        final String fromEmail = "marijaivkovic084@gmail.com";
        final String password = "svhzltqwelehdyuk";
        final String toEmail = getEmailFromUserId(ProjectApplication.getLoggedUser().getId());

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Order confirmation", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(getEmailFromUserId(ProjectApplication.getLoggedUser().getId()), false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            // Attach the file
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File(filePath));
            multipart.addBodyPart(attachmentBodyPart);

            msg.setContent(multipart);

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
