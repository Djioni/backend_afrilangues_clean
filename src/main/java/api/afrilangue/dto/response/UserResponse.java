package api.afrilangue.dto.response;

import api.afrilangue.models.Address;
import api.afrilangue.models.Language;
import api.afrilangue.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Set<Role> roles;
    private Set<Language> language;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
    private Integer isNumberPoint;
}
