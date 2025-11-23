package api.afrilangue.helpers;

import api.afrilangue.dto.response.DefaultResponse;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public class DefaultPayloadBuilder {

    public static DefaultResponse getDefaultResponse() {
        return api.afrilangue.dto.response.DefaultResponse.builder()
                .data("A été effectuée avec succès")
                .build();
    }
}
