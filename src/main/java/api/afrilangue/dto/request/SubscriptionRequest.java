package api.afrilangue.dto.request;

import api.afrilangue.models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequest {
    private String token;
    private String coupon;
    private String title;
    private String description;
    private PaymentMethod paymentMethod;
    private String userId;
    private String duration;
}
