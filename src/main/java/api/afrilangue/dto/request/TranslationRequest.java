package api.afrilangue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationRequest {
    private String typeStructure;
    private String name;
    private String function;
    private String email;
    private String phone;
    private String postalAddress;
    private List<TranslationPrestation> prestations;

}
