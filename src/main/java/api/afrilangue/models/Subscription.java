package api.afrilangue.models;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Document(collection = "subscription")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentMethod paymentMethod;
    private String subscriptionId;
    private String chargeId;
    private String duration;
    @DBRef
    private User user;
    boolean isActive;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
