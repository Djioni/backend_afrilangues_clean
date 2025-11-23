package api.afrilangue.serrvices.theme;

import api.afrilangue.dto.request.ThemeRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.helpers.Cleaning;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Language;
import api.afrilangue.models.Theme;
import api.afrilangue.repositories.ThemeRepository;
import api.afrilangue.serrvices.language.LanguageService;
import api.afrilangue.serrvices.mediaobject.MediaObjectService;
import api.afrilangue.serrvices.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static api.afrilangue.helpers.DefaultPayloadBuilder.getDefaultResponse;
import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository repository;
    private final LanguageService languageService;
    private final UserInfoService userInfoService;
    private final Cleaning cleaning;
    private final MediaObjectService mediaObjectService;

    @Override
    public Theme create(ThemeRequest theme) {
        if (theme.getName().isEmpty() || theme.getName() == null) {
            throw new IllegalArgumentException("Le nom du thème ne doit être vide ou nul");
        }
        if (repository.existsByName(theme.getName())) {
            throw new IllegalArgumentException("Le nom du thème est déjà enregistré");
        }
        if (theme.getImage().isEmpty() || theme.getImage() == null) {
            throw new IllegalArgumentException("L'image du thème ne doit être vide ou nulle");
        }
        if (theme.getLanguage().isEmpty() || theme.getLanguage() == null) {
            throw new IllegalArgumentException("La langue ne doit pas être vide ou nulle ");
        }

        Language language = languageService.find(theme.getLanguage());

        if (language == null) {
            throw new IllegalArgumentException("La langue ne doit pas être nulle");
        }

        Theme buildTheme = buildTheme(theme, language);

        return repository.save(buildTheme);
    }


    @Override
    public Theme find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("Le thème " + id + " n'existe pas");
        });
    }

    @Override
    public List<Theme> findAll() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseGet(() -> {
                    throw new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée");
                }).getName();

        List<Theme> themes = repository.findAll()
                .stream()
                .filter(theme -> user.getLanguage().stream()
                        .anyMatch(language -> language!=null && language.equals(theme.getLanguage())))
                .toList();
        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll() : themes;
    }

    @Override
    public DefaultResponse update(String id, Theme object) {
        Theme saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Theme object = find(id);
        cleaning.deleteLessonByTheme(object);
        if (object.getImage() != null) {
            mediaObjectService.deleteFile(object.getImage());
        }
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public List<Theme> findByLanguage(String id) {
        Language language = languageService.find(id);
        if (language == null) {
            throw new IllegalArgumentException("La langue ne doit pas être nulle");
        }
        return repository.findByLanguage(language);
    }

    private Theme buildTheme(ThemeRequest theme, Language language) {
        return Theme.builder()
                .name(theme.getName())
                .language(language)
                .image(theme.getImage())
                .createdByUser(recoveryUser())
                .build();
    }
}
