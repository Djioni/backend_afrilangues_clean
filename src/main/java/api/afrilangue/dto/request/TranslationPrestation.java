package api.afrilangue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationPrestation {
    private String typeService;
    private String date;
    private String redirectionLink;
    private String sourceLanguage;
    private String targetLanguage;
}
