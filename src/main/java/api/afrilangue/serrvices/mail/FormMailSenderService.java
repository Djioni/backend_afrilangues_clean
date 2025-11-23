package api.afrilangue.serrvices.mail;

import api.afrilangue.dto.request.InterpretingRequest;
import api.afrilangue.dto.request.TranslationRequest;
import api.afrilangue.dto.response.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class FormMailSenderService {
    public static final String MAIL = "contact@afrilangues.fr";
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public DefaultResponse sendEmailInterpreting(InterpretingRequest request) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("typeStructure", request.getTypeStructure());
        context.setVariable("name", request.getName());
        context.setVariable("function", request.getFunction());
        context.setVariable("email", request.getEmail());
        context.setVariable("phone", request.getPhone());
        context.setVariable("postalAddress", request.getPostalAddress());
        context.setVariable("prestation", request.getPrestations());
        String html = templateEngine.process("interpreting", context);
        String destinataires = request.getEmail() +", "+ MAIL;
        InternetAddress[] addresses = InternetAddress.parse(destinataires.replaceAll("\\s+", ""));
        helper.setTo(addresses);
        helper.setText(html, true);
        helper.setSubject("RÉSERVATION DE PRESTATION D’INTERPRÉTARIAT");
        helper.setFrom("noreply@afrilangues.fr");
        emailSender.send(message);

        return DefaultResponse.builder()
                .data("Réservation de prestation d'interprétation effectuée avec succès")
                .build();

    }

    public DefaultResponse sendEmailTranslation(TranslationRequest request) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("typeStructure", request.getTypeStructure());
        context.setVariable("name", request.getName());
        context.setVariable("function", request.getFunction());
        context.setVariable("email", request.getEmail());
        context.setVariable("phone", request.getPhone());
        context.setVariable("postalAddress", request.getPostalAddress());
        context.setVariable("prestation", request.getPrestations());
        String html = templateEngine.process("translation", context);
        String destinataires = request.getEmail() +", "+ MAIL;
        InternetAddress[] addresses = InternetAddress.parse(destinataires.replaceAll("\\s+", ""));
        helper.setTo(addresses);
        helper.setText(html, true);
        helper.setSubject("DEMANDE DE DEVIS GRATUIT");
        helper.setFrom("noreply@afrilangues.fr");
        emailSender.send(message);

        return DefaultResponse.builder()
                .data("Réservation de prestation d'interprétation effectuée avec succès")
                .build();

    }
}
