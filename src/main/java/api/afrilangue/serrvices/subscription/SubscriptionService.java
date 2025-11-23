package api.afrilangue.serrvices.subscription;

import api.afrilangue.dto.request.SubscriptionRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Subscription;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface SubscriptionService {
    Subscription create(SubscriptionRequest request);

    Subscription find(String id);
    List<Subscription> findSubscriptionByUser(String userId);

    List<Subscription> findAll();

    DefaultResponse update(String id, Subscription subscription);

    DefaultResponse delete(String id);

    void updateSubscription();

    String checkVoucher(String code);


}
