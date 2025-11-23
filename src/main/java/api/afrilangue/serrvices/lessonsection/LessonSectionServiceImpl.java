package api.afrilangue.serrvices.lessonsection;

import api.afrilangue.dto.request.LessonSectionRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.helpers.Cleaning;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Lesson;
import api.afrilangue.models.LessonSection;
import api.afrilangue.repositories.LessonSectionRepository;
import api.afrilangue.serrvices.lesson.LessonService;
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
public class LessonSectionServiceImpl implements LessonSectionService {
    private final LessonSectionRepository repository;
    private final LessonService lessonService;
    private final UserInfoService userInfoService;
    private final Cleaning cleaning;
    private final MediaObjectService mediaObjectService;

    @Override
    public LessonSection create(LessonSectionRequest lessonSectionRequest) {
        if (lessonSectionRequest.getLesson() == null || lessonSectionRequest.getLesson().isEmpty() ) {
            throw new IllegalArgumentException("La leçon ne doit pas être vide ou nulle");
        }
        if (lessonSectionRequest.getTitle() == null || lessonSectionRequest.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Le titre de la section ne doit être vide ou nul");
        }

        Lesson lesson = lessonService.find(lessonSectionRequest.getLesson());

        if (lesson == null) {
            throw new IllegalArgumentException("La leçon ne doit pas être vide ou nulle");
        }

        LessonSection buildLessonSection = buildLessonSection(lessonSectionRequest, lesson);

        return repository.save(buildLessonSection);
    }

    @Override
    public LessonSection find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("La section de la leçon " + id + " n'existe pas");
        });
    }

    @Override
    public List<LessonSection> findAll() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseGet(() -> {
                    throw new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée");
                }).getName();
        List<LessonSection> lessonSections = repository.findAll()
                .stream()
                .filter(lessonSection -> user.getLanguage().stream()
                        .anyMatch(language -> language !=null && language.equals(lessonSection.getLesson().getTheme().getLanguage())))
                .toList();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll() : lessonSections;
    }

    @Override
    public DefaultResponse update(String id, LessonSection object) {
        LessonSection saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        LessonSection object = find(id);
        cleaning.deleteExerciseByLessonSection(object);
        if (object.getImage() != null) {
            mediaObjectService.deleteFile(object.getImage());
        }
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public List<LessonSection> findByLesson(String id) {
        Lesson lesson = lessonService.find(id);
        if (lesson == null) {
            throw new IllegalArgumentException("La leçon ne doit pas être vide ou nulle");
        }
        return repository.findByLesson(lesson);
    }

    private LessonSection buildLessonSection(LessonSectionRequest lessonSectionRequest, Lesson lesson) {
        return LessonSection.builder()
                .content(lessonSectionRequest.getContent())
                .lesson(lesson)
                .title(lessonSectionRequest.getTitle())
                .image(lessonSectionRequest.getImage())
                .createdByUser(recoveryUser())
                .build();
    }
}
