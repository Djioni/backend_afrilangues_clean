package api.afrilangue.controllers;

import api.afrilangue.dto.request.InterpretingRequest;
import api.afrilangue.dto.request.TranslationRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.serrvices.mail.FormMailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/form")
public class FormController {
    private final FormMailSenderService service;


    @PostMapping("/interpreting")
    public DefaultResponse sendMailInterpreting(@RequestBody InterpretingRequest request) throws UnsupportedEncodingException, MessagingException {
        return service.sendEmailInterpreting(request);
    }

    @PostMapping("/translation")
    public DefaultResponse sendMailTranslation(@RequestBody TranslationRequest request) throws UnsupportedEncodingException, MessagingException {
        return service.sendEmailTranslation(request);
    }
}
