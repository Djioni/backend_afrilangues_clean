package api.afrilangue.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Document(collection = "assessment")
@Getter
@Setter
@Builder
public class Assessment {
    @Id
    private String id;
    private User user;
    private Integer nbPoint;
}
