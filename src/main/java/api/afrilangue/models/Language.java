package api.afrilangue.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Document(collection = "language")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Language {
    @Id
    private String id;
    private String name;
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
