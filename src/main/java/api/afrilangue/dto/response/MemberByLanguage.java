package api.afrilangue.dto.response;

import api.afrilangue.models.Language;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
public class MemberByLanguage {
    private Language language;
    private Integer member;
}
