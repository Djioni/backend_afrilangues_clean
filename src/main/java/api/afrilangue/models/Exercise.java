package api.afrilangue.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Document(collection = "exercise")
@Getter
@Setter
@Builder
public class Exercise {
    @Id
    private String id;
    private String title;
    private ExerciseType type;
    private Set<ExerciseAndAnswers> exerciseAndAnswers;
    private int difficultyLevel;
    private List<ExerciseMedia> exerciseMedia;
    private List<String> exerciseWords;
    @DBRef
    private LessonSection lessonSection;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
