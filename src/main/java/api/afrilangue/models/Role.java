package api.afrilangue.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Document(collection = "roles")
@Getter
@Builder
public class Role {
    @Id
    private String id;
    private ERole name;
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
