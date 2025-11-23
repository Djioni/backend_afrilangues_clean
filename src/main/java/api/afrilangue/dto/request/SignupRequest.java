package api.afrilangue.dto.request;

import api.afrilangue.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> roles;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private Set<String> language;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
}
