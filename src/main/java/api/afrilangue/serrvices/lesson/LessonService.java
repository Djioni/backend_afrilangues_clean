package api.afrilangue.serrvices.lesson;

import api.afrilangue.dto.request.LessonRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Lesson;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface LessonService {
    DefaultResponse create(LessonRequest lesson);

    Lesson find(String id);

    List<Lesson> findAll();

    DefaultResponse update(String id, Lesson lesson);

    DefaultResponse delete(String id);

    List<Lesson> findByTheme(String theme);
}
