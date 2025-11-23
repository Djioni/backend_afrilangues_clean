package api.afrilangue.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;


/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
@Document(collection = "passwordConfirmationToken")
@Data
public class PasswordConfirmationToken {
    @Id
    private String id;

    private String token;

    @DBRef
    private User user;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate = Instant.now();

    private boolean isActive = true;

}
