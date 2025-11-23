package api.afrilangue.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@Builder
public class Mail {
    private String from;
    private String mailTo;
    private String mailToOne;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;
}
