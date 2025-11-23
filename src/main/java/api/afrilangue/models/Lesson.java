package api.afrilangue.models;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "lesson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    @Id
    @ToString.Include
    private String id;
    @ToString.Include
    private String name;
    @DBRef
    private Theme theme;
    private String image;
    @DBRef
    private List<LessonSection> lessonSections;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lessonSections=" + (lessonSections != null ? lessonSections.size() : "null") +  // Affiche la taille de la liste de sections de leçon
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
