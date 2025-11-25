package api.afrilangue.serrvices;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
public class CaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.recaptcha.site}")
    private String recaptchaSiteKey;

    @Value("${google.recaptcha.verify.url}")
    private String recaptchaVerifyUrl;

    private final RestTemplate restTemplate = new RestTemplate();



    public String getSiteKey() {
        return recaptchaSiteKey;
    }

    public boolean verifyCaptcha(String recaptchaToken) {
        if (recaptchaToken == null || recaptchaToken.isEmpty()) {
            logger.warn("Le token reCAPTCHA est vide ou nul.");
            return false;
        }

        try {

            String url = String.format("%s?secret=%s&response=%s", recaptchaVerifyUrl, recaptchaSecret, recaptchaToken);


            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                logger.error("La réponse de l'API reCAPTCHA est nulle.");
                return false;
            }


            boolean success = (Boolean) response.getOrDefault("success", false);
            if (!success) {
                logger.warn("La validation du reCAPTCHA a échoué : {}", response);
            }
            return success;

        } catch (Exception e) {
            logger.error("Erreur lors de la validation du reCAPTCHA : ", e);
            return false;
        }
    }
}
