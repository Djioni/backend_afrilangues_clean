package api.afrilangue.helpers;

import api.afrilangue.models.*;
import api.afrilangue.repositories.ExerciseRepository;
import api.afrilangue.repositories.LessonRepository;
import api.afrilangue.repositories.LessonSectionRepository;
import api.afrilangue.repositories.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class Cleaning {
    private final ExerciseRepository exerciseRepository;
    private final LessonSectionRepository lessonSectionRepository;
    private final LessonRepository lessonRepository;
    private final ThemeRepository themeRepository;

    public void deleteExerciseByLessonSection(LessonSection lessonSection) {
        List<Exercise> exercises = exerciseRepository.findByLessonSectionOrderByCreatedAtAsc(lessonSection);
        if (exercises != null && !exercises.isEmpty()) {
            exerciseRepository.deleteAll(exercises);
        }
    }

    public void deleteLessonSectionByLesson(Lesson lesson) {
        List<LessonSection> lessonSections = lessonSectionRepository.findByLesson(lesson);
        if (lessonSections != null && !lessonSections.isEmpty()) {
            lessonSections.forEach(this::deleteExerciseByLessonSection);
            lessonSectionRepository.deleteAll(lessonSections);
        }
    }

    public void deleteLessonByTheme(Theme theme) {
        List<Lesson> lessons = lessonRepository.findByTheme(theme);
        if (lessons != null && !lessons.isEmpty()) {
            lessons.forEach(this::deleteLessonSectionByLesson);
            lessonRepository.deleteAll(lessons);
        }
    }

    public void deleteThemeByLanguage(Language language) {
        List<Theme> themes = themeRepository.findByLanguage(language);
        if (themes != null && !themes.isEmpty()) {
            themes.forEach(this::deleteLessonByTheme);
            themeRepository.deleteAll(themes);
        }
    }
}
