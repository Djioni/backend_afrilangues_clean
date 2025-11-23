package api.afrilangue.service;

import api.afrilangue.AfrilangueApplicationTests;
import api.afrilangue.models.Mail;
import api.afrilangue.serrvices.mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public class MailSenderServiceTest extends AfrilangueApplicationTests {
    @Autowired
    private MailSenderService mailSenderService;

    @Test
    public void test_send_email() throws MessagingException {
        Map<String, Object> props = Map.of( "name","Ibra");
        Mail mail = Mail.builder()
                .mailTo("iibdiallo@gmail.com")
                .from("noreply@afrilangues.fr")
                .subject("Confirmation création de compte")
                .props(props)
                .build();
        mailSenderService.sendEmail(mail);
    }

    @Test
    public void test_send_email_password_forgot() throws MessagingException {
        Map<String, Object> props = Map.of( "name","Ibra",
                "token", "12334xffggsssz");
        Mail mail = Mail.builder()
                .mailTo("iibdiallo@gmail.com")
                .from("noreply@afrilangues.fr")
                .subject("Récupération de mot de passe")
                .props(props)
                .build();
        mailSenderService.sendEmailPasswordForgot(mail);
    }
}
