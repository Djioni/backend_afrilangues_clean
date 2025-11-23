package api.afrilangue.serrvices.exercice;

import api.afrilangue.dto.request.ExercisesRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Exercise;
import api.afrilangue.models.LessonSection;
import api.afrilangue.repositories.ExerciseRepository;
import api.afrilangue.serrvices.lessonsection.LessonSectionService;
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
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository repository;
    private final LessonSectionService lessonSectionService;
    private final UserInfoService userInfoService;
    private final MediaObjectService mediaObjectService;

    @Override
    public DefaultResponse create(ExercisesRequest exercisesRequest) {
        if (exercisesRequest.getLessonSection() == null) {
            throw new IllegalArgumentException("La section de l'exercice ne doit pas être vide ou nulle");
        }
        if (exercisesRequest.getTitle().isEmpty() || exercisesRequest.getTitle() == null) {
            throw new IllegalArgumentException("Le titre de l'exercice ne doit être vide ou nul");
        }
        if (exercisesRequest.getExerciseAndAnswers().isEmpty() || exercisesRequest.getExerciseAndAnswers() == null) {
            throw new IllegalArgumentException("exerciseAndAnswers de l'exercice ne doit être vide ou nul");
        }

        LessonSection lessonSection = lessonSectionService.find(exercisesRequest.getLessonSection());

        if (lessonSection == null) {
            throw new IllegalArgumentException("La section de l'exercice ne doit être vide ou nul");
        }

        Exercise exercise = buildExercise(exercisesRequest, lessonSection);

        repository.save(exercise);

        return getDefaultResponse();
    }

    @Override
    public Exercise find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("L'exercice " + id + " n'existe pas");
        });
    }

    @Override
    public List<Exercise> findAll() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseGet(() -> {
                    throw new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée");
                }).getName();

        List<Exercise> exercises = repository.findAllByOrderByCreatedAtAsc()
                .stream()
                .filter(exercise -> user.getLanguage().stream()
                        .anyMatch(language -> language!=null && language.equals(exercise.getLessonSection().getLesson().getTheme().getLanguage())))
                .toList();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAllByOrderByCreatedAtAsc() : exercises;
    }

    @Override
    public DefaultResponse update(String id, Exercise exercise) {
        Exercise saved = find(id);
        exercise.setId(saved.getId());
        exercise.setModifiedByUser(recoveryUser());
        repository.save(exercise);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Exercise object = find(id);
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public List<Exercise> findByLessonSection(String id) {
        LessonSection lessonSection = lessonSectionService.find(id);
        if (lessonSection == null) {
            throw new IllegalArgumentException("La section de l'exercice ne doit être vide ou nul");
        }
        return repository.findByLessonSectionOrderByCreatedAtAsc(lessonSection);
    }

    private Exercise buildExercise(ExercisesRequest exercisesRequest, LessonSection lessonSection) {
        return Exercise.builder()
                .lessonSection(lessonSection)
                .type(exercisesRequest.getTypes())
                .title(exercisesRequest.getTitle())
                .exerciseMedia(exercisesRequest.getExerciseMedia())
                .exerciseWords(exercisesRequest.getExerciseWords())
                .exerciseAndAnswers(exercisesRequest.getExerciseAndAnswers())
                .createdByUser(recoveryUser())
                .build();
    }
}
