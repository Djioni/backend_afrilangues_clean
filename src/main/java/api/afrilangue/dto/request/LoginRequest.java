package api.afrilangue.dto.request;

import javax.validation.constraints.NotBlank;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
