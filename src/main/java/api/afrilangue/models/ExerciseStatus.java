package api.afrilangue.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Document(collection = "exercise_status")
@Getter
@Setter
@Builder
public class ExerciseStatus {
    @Id
    private String id;
    @DBRef
    private Exercise exercise;
    @DBRef(lazy = true)
    private User user;
    private StatusEnum status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
