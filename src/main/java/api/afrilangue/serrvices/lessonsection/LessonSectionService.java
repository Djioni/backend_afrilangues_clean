package api.afrilangue.serrvices.lessonsection;

import api.afrilangue.dto.request.LessonSectionRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.LessonSection;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface LessonSectionService {
    LessonSection create(LessonSectionRequest lessonSection);

    LessonSection find(String id);

    List<LessonSection> findAll();

    DefaultResponse update(String id, LessonSection lessonSection);

    DefaultResponse delete(String id);

    List<LessonSection> findByLesson(String lesson);

}
