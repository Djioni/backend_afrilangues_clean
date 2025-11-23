package api.afrilangue.service;

import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Language;
import api.afrilangue.models.Lesson;
import api.afrilangue.models.Theme;
import api.afrilangue.repositories.LessonRepository;
import api.afrilangue.serrvices.lesson.LessonServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public class LessonServiceTest {
    @InjectMocks
    private LessonServiceImpl service;
    @Mock
    private LessonRepository repository;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
/*
    @Test
    public void test_lesson_create() {
        Lesson lessonRequest = buildLesson("test");
        Mockito.when(repository.save(lessonRequest)).thenReturn(lessonRequest);
        DefaultResponse response = service.create(lessonRequest);
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData()).isEqualTo("Leçon enregistre avec succes!!!");

    }
*/

    @Test
    public void test_lesson_find() {
        Lesson lessonRequest = buildLesson("test");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(lessonRequest));
        Lesson lesson = service.find("1");
        assertThat(lesson).isNotNull();
        assertThat(lesson.getTheme()).isNotNull();
        assertThat(lesson.getTheme().getLanguage()).isNotNull();
        assertThat(lesson.getName()).isNotNull();

    }

    @Test
    public void test_lesson_delete() {
        Lesson lessonRequest = buildLesson("test");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(lessonRequest));
        DefaultResponse response = service.delete("1");
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");

    }

    @Test
    public void test_lesson_update() {
        Lesson lessonRequest = buildLesson("test");
        Lesson lessonRequestUpdate = buildLesson("test lesson");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(lessonRequest));
        DefaultResponse response = service.update("1", lessonRequestUpdate);
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");

    }

    @Test
    public void test_lesson_find_all() {
        Lesson lessonRequest = buildLesson("test theme");
        Mockito.when(repository.findAll()).thenReturn(List.of(lessonRequest));
        List<Lesson> lessons = service.findAll();
        assertThat(lessons).isNotNull();
        assertThat(lessons.stream().findFirst().get().getName()).isEqualTo("test theme");
        assertThat(lessons.stream().findFirst().get().getTheme().getLanguage()).isNotNull();
        assertThat(lessons.stream().findFirst().get().getTheme()).isNotNull();

    }

    private Lesson buildLesson(String name) {
        Language language = Language.builder()
                .id("1")
                .name("test language")
                .image("test image")
                .build();

        Theme theme = Theme.builder()
                .id("1")
                .name(name)
                .language(language)
                .build();
        return Lesson.builder()
                .name(name)
                .theme(theme)
                .build();


    }
}
