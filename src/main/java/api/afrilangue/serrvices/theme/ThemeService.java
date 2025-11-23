package api.afrilangue.serrvices.theme;

import api.afrilangue.dto.request.ThemeRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Theme;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface ThemeService {
    Theme create(ThemeRequest theme);

    Theme find(String id);

    List<Theme> findAll();

    DefaultResponse update(String id, Theme theme);

    DefaultResponse delete(String id);

    List<Theme> findByLanguage(String language);
}
