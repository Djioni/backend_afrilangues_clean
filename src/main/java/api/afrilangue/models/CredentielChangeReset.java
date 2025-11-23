package api.afrilangue.models;

import lombok.Data;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
@Data
public class CredentielChangeReset {

    private String mail;

    private String password;

    private String confirmation;

}
