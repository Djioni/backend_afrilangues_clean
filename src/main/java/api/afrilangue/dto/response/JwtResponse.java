package api.afrilangue.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Builder
@Getter
public class JwtResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
}
