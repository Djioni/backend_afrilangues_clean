package api.afrilangue.serrvices;

import api.afrilangue.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Component
@RequiredArgsConstructor
public class UserCryptoListener extends AbstractMongoEventListener<User> {
    private final FieldEncryptor enc;

    private String encIfNeeded(String v) { return v == null ? null : enc.encrypt(v); }
    private String decIfNeeded(String v) { return v == null ? null : enc.decrypt(v); }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> e) {
        User u = e.getSource();
        u.setFirstName(encIfNeeded(u.getFirstName()));
        u.setLastName(encIfNeeded(u.getLastName()));
        u.setPhone(encIfNeeded(u.getPhone()));
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<User> e) {
        User u = e.getSource();
        u.setFirstName(decIfNeeded(u.getFirstName()));
        u.setLastName(decIfNeeded(u.getLastName()));
        u.setPhone(decIfNeeded(u.getPhone()));
    }
}
