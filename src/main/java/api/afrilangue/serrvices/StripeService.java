package api.afrilangue.serrvices;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
public class StripeService {
    @Value("${stripe.key.secret}")
    private String API_SECRET_KEY;

    public String createCustomer(String email, String token) {

        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);
            customerParams.put("source", token);

            Customer customer = Customer.create(customerParams);
            return customer.getId();

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public String createSubscription(String mail, String token, String product, String coupon) {
        if (mail == null || mail.isEmpty()) {
            throw new IllegalArgumentException("L'e-mail de paiement stripe est manquant. Veuillez réessayer plus tard.");
        }

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Le token de paiement stripe est manquant. Veuillez réessayer plus tard.");
        }

        String customerId = createCustomer(mail, token);

        if (customerId == null) {
            throw new IllegalArgumentException("Une erreur s'est produite lors de la tentative de création d'un client");
        }

        try {
            Stripe.apiKey = API_SECRET_KEY;

            Map<String, Object> item = new HashMap<>();

            item.put("price", product);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);

            if (!coupon.isEmpty()) {
                params.put("coupon", coupon);
            }

            Subscription subscription = Subscription.create(params);

            return subscription.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean cancelSubscription(String subscriptionId) {
        if (subscriptionId == null || subscriptionId.isEmpty()) {
            throw new IllegalArgumentException("subscriptionId est nul");
        }

        try {
            Subscription subscription = Subscription.retrieve(subscriptionId);
            subscription.cancel();
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public String retriveCoupon(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Coupon est nul");
        }
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Coupon coupon = Coupon.retrieve(code);
            if (coupon != null && coupon.getValid()) {
                String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
                        : coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
                return details;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return "son code promo n'est pas disponible. Cela peut être dû au fait qu'il a expiré ou qu'il a déjà été appliqué à votre compte.";

    }

    public String createCharge(String mail, String token, int amount) {
        if (mail == null || mail.isEmpty()) {
            throw new IllegalArgumentException("L'e-mail de paiement stripe est manquant. Veuillez réessayer plus tard.");
        }
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Le token de paiement stripe est manquant. Veuillez réessayer plus tard.");
        }
        try {
            Stripe.apiKey = API_SECRET_KEY;

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("description", "Charge for " + mail);
            chargeParams.put("currency", "EUR");
            chargeParams.put("amount", amount);
            chargeParams.put("source", token);

            Charge charge = Charge.create(chargeParams);

            return charge.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
