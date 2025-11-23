package api.afrilangue.serrvices.language;

import api.afrilangue.dto.request.LanguageRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.MemberByLanguage;
import api.afrilangue.models.Language;
import api.afrilangue.models.User;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface LanguageService {
    Language create(LanguageRequest language);

    Language find(String id);

    Language findByName(String name);

    List<Language> findAll();

    List<Language> findAllByUsername();

    DefaultResponse update(String id, Language language);

    DefaultResponse delete(String id);

    List<MemberByLanguage> getMemberByLanguages(List<User> users, List<Language> languages);
}
