package api.afrilangue.serrvices.language;

import api.afrilangue.dto.request.LanguageRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.MemberByLanguage;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.helpers.Cleaning;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Language;
import api.afrilangue.models.User;
import api.afrilangue.repositories.LanguageRepository;
import api.afrilangue.serrvices.mediaobject.MediaObjectService;
import api.afrilangue.serrvices.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static api.afrilangue.helpers.DefaultPayloadBuilder.getDefaultResponse;
import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository repository;
    private final Cleaning cleaning;
    private final UserInfoService userInfoService;
    private final MediaObjectService mediaObjectService;

    @Override
    public Language create(final LanguageRequest object) {
        String name = object.getName();

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Le nom de la langue ne doit être vide ou nul");
        }

        if (repository.existsLanguageByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Le nom de la langue est déjà enregistré");
        }

        String image = object.getImage();

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("L'image de la langue ne doit être vide ou nulle");
        }

        Language language = buildLanguage(name, image);

        return repository.save(language);
    }

    @Override
    public Language find(final String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("La langue " + id + " n'existe pas ");
        });
    }

    @Override
    public Language findByName(final String name) {
        return repository.findByNameIgnoreCase(name).orElseGet(() -> {
            throw new IllegalArgumentException("La langue " + name + " n'existe pas ");
        });
    }

    @Override
    public List<Language> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Language> findAllByUsername() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseGet(() -> {
                    throw new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée");
                }).getName();

        List<Language> languages = repository.findAll()
                .stream()
                .filter(language -> user.getLanguage().stream()
                        .anyMatch(userLanguage -> userLanguage!=null && userLanguage.equals(language)))
                .toList();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll() : languages;
    }

    @Override
    public DefaultResponse update(final String id, final Language object) {
        Language saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Language object = find(id);
        cleaning.deleteThemeByLanguage(object);
        if (object.getImage() != null) {
            mediaObjectService.deleteFile(object.getImage());
        }
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public List<MemberByLanguage> getMemberByLanguages(List<User> users, List<Language> languages) {
        List<MemberByLanguage> languageByUsers = new ArrayList<>();
        languages.forEach(language -> languageByUsers.add(MemberByLanguage.builder()
                .member(getCount(users, language))
                .language(language)
                .build())
        );
        return languageByUsers;
    }

    private Integer getCount(List<User> users, Language language) {
        return Math.toIntExact(users.stream()
                .flatMap(user -> user.getLanguage().stream())
                .filter(languages -> languages != null && languages.getName().equalsIgnoreCase(language.getName()))
                .count());
    }

    private Language buildLanguage(String name, String image) {
        return Language.builder()
                .name(name)
                .image(image)
                .createdByUser(recoveryUser())
                .build();
    }
}
