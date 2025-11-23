package api.afrilangue.models;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Document(collection = "lessonsection")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonSection {
    @Id
    private String id;

    private String title;

    private String content;
    @DBRef
    private Lesson lesson;

    private String image;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
