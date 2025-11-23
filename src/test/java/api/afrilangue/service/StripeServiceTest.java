package api.afrilangue.service;

import api.afrilangue.AfrilangueApplicationTests;
import api.afrilangue.serrvices.StripeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
class StripeServiceTest extends AfrilangueApplicationTests {
    @Autowired
    private StripeService stripeService;

    @Test
    void test_create_subscription() {
        String mail = "adminax1dd@fr.fr";
        String token = "tok_1OiekOHRPdZRapkq270PhktJ";
        String subscriptionId = stripeService.createSubscription(mail, token, "price_1OO9kkHRPdZRapkq2MNmk0fJ","");
        assertThat(subscriptionId).isNotNull().isNotEmpty();
    }

    @Test
    void test_cancel_subscription() {
        String mail = "adminax1dd@fr.fr";
        String token = "tok_1OiekOHRPdZRapkq270PhktJ";
        String subscriptionId = stripeService.createSubscription(mail, token, "price_1OO9kkHRPdZRapkq2MNmk0fJ","");
        boolean cancelSubscription = stripeService.cancelSubscription(subscriptionId);
        assertThat(cancelSubscription).isTrue();
    }

    @Test
    void test_create_charge() {
        String mail = "adminax1dd@fr.fr";
        String token = "tok_1OiekOHRPdZRapkq270PhktJ";
        String subscriptionId = stripeService.createCharge(mail, token, 1000);
        assertThat(subscriptionId).isNotNull().isNotEmpty();
    }
}
