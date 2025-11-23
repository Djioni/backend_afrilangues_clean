package api.afrilangue.serrvices;

import api.afrilangue.serrvices.advertisement.AdvertisementService;
import api.afrilangue.serrvices.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final AdvertisementService advertisementService;
    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 * * * * *")
    public void updateAdvertisement() {
        advertisementService.performUpdateAdvertisement();
        subscriptionService.updateSubscription();
    }
}
