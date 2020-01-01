package lk.excellent.pharamacy_management.util.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;


    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmployeeRegistrationEmail(String receiverEmail,String subject, String messageText) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setTo(receiverEmail);
            mailMessage.setFrom("Excellent_Health_Solution");
            mailMessage.setSubject(subject);
            mailMessage.setText(messageText);

            javaMailSender.send(mailMessage);
            System.out.println("successfully send");
        } catch (Exception e) {
            System.out.println("exception "+ e.toString());
            return false;
        }
        return true;
    }

    public boolean sendCustomerRegistrationEmail(String recieverEmail,String subject, String messageText) throws MailException {
               SimpleMailMessage mailMessage = new SimpleMailMessage();
     try {
         mailMessage.setTo(recieverEmail);
         mailMessage.setFrom("Excellent_Health_Solution");
         mailMessage.setSubject(subject);
         mailMessage.setText(messageText);

         javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean sendSupplierRegistrationEmail(String recieverEmail,String subject, String messageText) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setTo(recieverEmail);
            mailMessage.setFrom("Excellent_Health_Solution");
            mailMessage.setSubject(subject);
            mailMessage.setText(messageText);

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
