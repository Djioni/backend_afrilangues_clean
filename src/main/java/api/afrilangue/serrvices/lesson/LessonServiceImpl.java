package api.afrilangue.serrvices.lesson;

import api.afrilangue.dto.request.LessonRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.helpers.Cleaning;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Lesson;
import api.afrilangue.models.Theme;
import api.afrilangue.repositories.LessonRepository;
import api.afrilangue.serrvices.mediaobject.MediaObjectService;
import api.afrilangue.serrvices.theme.ThemeService;
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
public class LessonServiceImpl implements LessonService {
    private final LessonRepository repository;
    private final ThemeService themeService;
    private final UserInfoService userInfoService;
    private final Cleaning cleaning;
    private final MediaObjectService mediaObjectService;

    @Override
    public DefaultResponse create(LessonRequest lesson) {
        if(lesson.getName()==null || lesson.getName().isEmpty()){
            throw new IllegalArgumentException("Le titre de la leçon ne doit être vide ou nul" );
        }

        if(lesson.getTheme() == null || lesson.getTheme().isEmpty()){
            throw new IllegalArgumentException("Le theme ne doit pas être vide ou nul" );
        }

        Theme theme = themeService.find(lesson.getTheme());

        if(theme == null){
            throw new IllegalArgumentException("Le theme ne doit pas être vide ou nul" );
        }

        Lesson buildLesson = buildLesson(lesson, theme);

        repository.save(buildLesson);

        return getDefaultResponse();
    }


    @Override
    public Lesson find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("La leçon " + id + " n'existe pas");
        });
    }

    @Override
    public List<Lesson> findAll() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseGet(() -> {
                    throw new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée");
                }).getName();

        List<Lesson> lessons = repository.findAll()
                .stream()
                .filter(lesson -> user.getLanguage().stream()
                        .anyMatch(language -> language!=null && language.equals(lesson.getTheme().getLanguage())))
                .toList();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll() : lessons;
    }

    @Override
    public DefaultResponse update(String id, Lesson object) {
        Lesson saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Lesson object = find(id);
        cleaning.deleteLessonSectionByLesson(object);
        if (object.getImage() != null) {
            mediaObjectService.deleteFile(object.getImage());
        }
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public List<Lesson> findByTheme(String id) {
        Theme theme = themeService.find(id);
        if(theme == null){
            throw new IllegalArgumentException("Le theme ne doit pas être vide ou nul" );
        }
        return repository.findByTheme(theme);
    }

    private Lesson buildLesson(LessonRequest lesson, Theme theme) {
        return Lesson.builder()
                .name(lesson.getName())
                .theme(theme)
                .image(lesson.getImage())
                .createdByUser(recoveryUser())
                .build();
    }
}
