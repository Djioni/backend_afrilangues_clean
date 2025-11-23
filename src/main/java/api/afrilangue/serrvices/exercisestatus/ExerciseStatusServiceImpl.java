package api.afrilangue.serrvices.exercisestatus;

import api.afrilangue.dto.request.ExerciseStatusRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Exercise;
import api.afrilangue.models.ExerciseStatus;
import api.afrilangue.models.ExerciseType;
import api.afrilangue.models.Lesson;
import api.afrilangue.models.LessonSection;
import api.afrilangue.models.StatusEnum;
import api.afrilangue.models.Theme;
import api.afrilangue.models.User;
import api.afrilangue.repositories.ExerciseStatusRepository;
import api.afrilangue.serrvices.exercice.ExerciseService;
import api.afrilangue.serrvices.lesson.LessonService;
import api.afrilangue.serrvices.lessonsection.LessonSectionService;
import api.afrilangue.serrvices.theme.ThemeService;
import api.afrilangue.serrvices.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static api.afrilangue.helpers.DefaultPayloadBuilder.getDefaultResponse;
import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class ExerciseStatusServiceImpl implements ExerciseStatusService {
    public static final String TERMINE = "Terminé";
    public static final String EN_COURS = "En cours";
    private final ExerciseStatusRepository repository;
    private final ExerciseService exerciseService;
    private final UserInfoService userService;
    private final LessonSectionService lessonSectionService;
    private final LessonService lessonService;
    private final ThemeService themeService;

    @Override
    public ExerciseStatus create(ExerciseStatusRequest request) {
        if (request.getExerciseId() == null || request.getExerciseId().isEmpty()) {
            throw new IllegalArgumentException("L'id de l'exercise ne doit pas être vide ou nul");
        }
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new IllegalArgumentException("L'id de l'utilisateur ne doit pas être vide ou nul");
        }

        Exercise exercise = exerciseService.find(request.getExerciseId());
        User user = userService.find(request.getUserId());
        if(repository.existsExerciseStatusByUserAndExercise(user,exercise)){
            throw new IllegalArgumentException("L'utilisateur a déjà été évalué pour cet exercice");
        }
        ExerciseStatus exerciseStatus = ExerciseStatus.builder()
                .status(StatusEnum.FINISHED)
                .exercise(exercise)
                .user(user)
                .build();

        return repository.save(exerciseStatus);
    }

    @Override
    public ExerciseStatus find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("Le statut de l'exercice " + id + " n'existe pas ");
        });
    }

    @Override
    public List<ExerciseStatus> findAll() {
        return repository.findAll();
    }

    @Override
    public DefaultResponse update(String id, ExerciseStatus object) {
        ExerciseStatus saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        ExerciseStatus object = find(id);
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse findExerciseStatusByUser(String userId, String exerciseId) {
        Exercise exercise = exerciseService.find(exerciseId);
        User user = userService.find(userId);
        ExerciseStatus exerciseStatus = repository.findExerciseStatusByUserAndExercise(user, exercise);
        return DefaultResponse.builder()
                .data(exerciseStatus != null ? TERMINE : EN_COURS)
                .build();
    }

    @Override
    public DefaultResponse findLessonSectionStatusByUser(String userId, String lessonSectionId) {
        User user = userService.find(userId);
        LessonSection lessonSection = lessonSectionService.find(lessonSectionId);
        List<ExerciseStatus> exerciseStatuses = repository.findExerciseStatusByUser(user);
        List<Exercise> exercises = getExercises(exerciseStatuses, lessonSection);
        List<Exercise> exerciseByLessonSections = getExercises(lessonSectionId);

        boolean check = (exercises.size() == exerciseByLessonSections.size() && !exercises.isEmpty()) || exerciseByLessonSections.isEmpty();
        return DefaultResponse.builder()
                .data(check ? TERMINE : EN_COURS)
                .build();
    }

    private List<Exercise> getExercises(String lessonSectionId) {
        Set<String> prefixes = Set.of(ExerciseType.LISTEN.name(), ExerciseType.MEMORY.name(), ExerciseType.TEXT_ZONE.name());
        return exerciseService.findByLessonSection(lessonSectionId).stream()
                .filter(section -> section.getLessonSection() != null
                        && prefixes.stream().noneMatch(prefix -> section.getType().name().equalsIgnoreCase(prefix)))
                .toList();

    }

    @Override
    public DefaultResponse findLessonByUser(String userId, String lessonId) {
        User user = userService.find(userId);
        Lesson lesson = lessonService.find(lessonId);
        List<ExerciseStatus> exerciseStatuses = repository.findExerciseStatusByUser(user);
        int lessonSectionsSize = getLessons(exerciseStatuses, lesson);
        List<LessonSection> lessonSectionsByExercise = getLessonSections(lesson);
        boolean check = (lessonSectionsSize == lessonSectionsByExercise.size() && lessonSectionsSize!=0) || lessonSectionsByExercise.isEmpty();
        return DefaultResponse.builder()
                .data(check ? TERMINE : EN_COURS)
                .build();
    }

    private List<LessonSection> getLessonSections(Lesson lesson) {
        Set<String> prefixes = Set.of("Objectifs", "Grammaire");
        return lessonSectionService
                .findByLesson(lesson.getId())
                .stream()
                .filter(section -> section != null && !getExercises(section.getId()).isEmpty() && prefixes.stream().noneMatch(prefix -> section.getTitle().startsWith(prefix)))
                .toList();
    }

    @Override
    public DefaultResponse findThemeByUser(String userId, String themeId) {
        User user = userService.find(userId);
        Theme theme = themeService.find(themeId);
        List<ExerciseStatus> exerciseStatuses = repository.findExerciseStatusByUser(user);
        int lessons = getLessons(exerciseStatuses, theme);
        List<Lesson> lessonByThemes = lessonService.findByTheme(themeId)
                .stream().filter(lesson -> lesson!=null && !getLessonSections(lesson).isEmpty())
                .toList();
        boolean check = lessons == lessonByThemes.size() && lessons!=0;
        return DefaultResponse.builder()
                .data(check ? TERMINE : EN_COURS)
                .build();
    }

    private int getLessons(List<ExerciseStatus> exerciseStatuses, Theme theme) {

        Set<String> uniqueLessons = exerciseStatuses.stream()
                .filter(exerciseStatus -> exerciseStatus != null
                        && StatusEnum.FINISHED.equals(exerciseStatus.getStatus())
                        && exerciseStatus.getExercise() != null
                        && exerciseStatus.getExercise().getLessonSection() != null
                        && exerciseStatus.getExercise().getLessonSection().getLesson() != null
                        && theme.getId().equals(exerciseStatus.getExercise().getLessonSection().getLesson().getTheme().getId()))
                .map(exerciseStatus -> exerciseStatus.getExercise().getLessonSection().getId())
                .collect(Collectors.toSet());
        return uniqueLessons.size();
    }

    private int getLessons(List<ExerciseStatus> exerciseStatuses, Lesson lesson) {
        if (exerciseStatuses == null || lesson == null) {
            return 0;
        }

        Set<String> uniqueLessonSections = exerciseStatuses.stream()
                .filter(exerciseStatus -> exerciseStatus != null
                        && StatusEnum.FINISHED.equals(exerciseStatus.getStatus())
                        && exerciseStatus.getExercise() != null
                        && exerciseStatus.getExercise().getLessonSection() != null
                        && lesson.getId().equals(exerciseStatus.getExercise().getLessonSection().getLesson().getId()))
                .map(exerciseStatus -> exerciseStatus.getExercise().getLessonSection().getId())
                .collect(Collectors.toSet());

        return uniqueLessonSections.size();
    }




    private List<Exercise> getExercises(List<ExerciseStatus> exerciseStatuses, LessonSection lessonSection) {
        if (exerciseStatuses == null || lessonSection == null) {
            return Collections.emptyList();
        }

        return exerciseStatuses.stream()
                .filter(exerciseStatus ->
                        exerciseStatus != null
                                && exerciseStatus.getStatus() == StatusEnum.FINISHED
                                && exerciseStatus.getExercise() != null
                                && lessonSection.getId().equalsIgnoreCase(exerciseStatus.getExercise().getLessonSection().getId()))
                .map(ExerciseStatus::getExercise)
                .toList();
    }

}
