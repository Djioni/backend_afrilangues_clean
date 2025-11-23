package api.afrilangue.serrvices.subscription;

import api.afrilangue.dto.request.SubscriptionRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.models.ERole;
import api.afrilangue.models.PaymentMethod;
import api.afrilangue.models.Subscription;
import api.afrilangue.models.User;
import api.afrilangue.repositories.SubscriptionRepository;
import api.afrilangue.serrvices.StripeService;
import api.afrilangue.serrvices.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static api.afrilangue.helpers.DefaultPayloadBuilder.getDefaultResponse;
import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserInfoService userService;
    private final SubscriptionRepository repository;
    private final UserInfoService userInfoService;
    private final StripeService stripeService;


    @Override
    public Subscription create(SubscriptionRequest request) {
        String chargeId = null;
        String subscriptionId = null;
        LocalDate currentDate = LocalDate.now();
        if (request.getToken() == null || request.getToken().isEmpty()) {
            throw new IllegalArgumentException("Token est nul ou vide");
        }
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User est nul ou vide");
        }
        if (request.getPaymentMethod() == null || request.getPaymentMethod().name().isEmpty()) {
            throw new IllegalArgumentException("Moyen de paiement est nul ou vide");
        }
        if (request.getDuration() == null || !List.of("1", "12").contains(request.getDuration())) {
            throw new IllegalArgumentException("La durée doit être égale à 1 ou 12");
        }
        User user = userService.find(request.getUserId());
        boolean checkSubscription = findSubscriptionByUser(user.getId())
                .stream().anyMatch(Subscription::isActive);
        if (checkSubscription) {
            throw new IllegalArgumentException("L'utilisateur a déjà une souscription en cours. Il ne peut pas souscrire deux fois avec le même compte");
        }
        if (PaymentMethod.CB.equals(request.getPaymentMethod()) && "1".equals(request.getDuration())) {
            subscriptionId = stripeService.createSubscription(user.getEmail(),
                    request.getToken(),
                    "price_1OO9kkHRPdZRapkq2MNmk0fJ",
                    request.getCoupon());
        }

        if (PaymentMethod.CB.equals(request.getPaymentMethod()) && "12".equals(request.getDuration())) {
            chargeId = stripeService.createCharge(user.getEmail(), request.getToken(), 1000);

        }
        LocalDate endDate = currentDate.plusMonths(Integer.parseInt(request.getDuration()));

        Subscription buildSubscription = Subscription.builder()
                .user(user)
                .subscriptionId(subscriptionId)
                .title(request.getTitle())
                .duration(request.getDuration())
                .startDate(currentDate)
                .paymentMethod(request.getPaymentMethod())
                .endDate(endDate)
                .isActive(true)
                .chargeId(chargeId)
                .build();

        return repository.save(buildSubscription);
    }

    @Override
    public Subscription find(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("La subscription " + id + " n'existe pas"));
    }

    @Override
    public List<Subscription> findSubscriptionByUser(String userId) {
        User user = userInfoService.find(userId);
        return repository.findSubscriptionByUser(user);
    }

    @Override
    public List<Subscription> findAll() {
        UserResponse user = userInfoService.getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée")).getName();

        List<Subscription> subscriptions = repository.findAll()
                .stream()
                .filter(subscription -> user.getEmail().equals(subscription.getUser().getEmail()))
                .toList();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll() : subscriptions;
    }

    @Override
    public DefaultResponse update(String id, Subscription subscription) {
        Subscription saved = find(id);
        subscription.setId(saved.getId());
        subscription.setModifiedByUser(recoveryUser());
        repository.save(subscription);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Subscription object = find(id);
        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public void updateSubscription() {
        List<Subscription> subscriptions = repository.findAll();
        subscriptions.forEach(subscription -> {
            LocalDate currentDate = LocalDate.now();
            if (subscription.getEndDate().isBefore(currentDate)) {
                subscription.setActive(false);
            }
            repository.save(subscription);
        });
    }

    @Override
    public String checkVoucher(String code) {
        return stripeService.retriveCoupon(code);
    }
}
