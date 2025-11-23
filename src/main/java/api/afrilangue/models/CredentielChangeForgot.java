package api.afrilangue.models;

import lombok.Data;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
@Data
public class CredentielChangeForgot {

    private String token;

    private String password;

    private String Confirmation;
}
