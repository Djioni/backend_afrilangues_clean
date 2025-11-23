package api.afrilangue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageRequest {
    private String name;
    private String image;
}
