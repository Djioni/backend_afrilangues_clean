package api.afrilangue.models;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Document(collection = "advertisement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Advertisement {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    @Builder.Default
    private boolean isActive = true;
    private List<ExerciseMedia> exerciseMedia;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
