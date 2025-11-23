package api.afrilangue.dto.request;

import api.afrilangue.models.Address;
import api.afrilangue.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String id;
    private String username;
    private String email;
    private Set<String> roles;
    private Set<Language> language;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
}
