package api.afrilangue.dto.request;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

public class ApiErrorResponse {

    private String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public static ApiErrorResponse from(String errorMessage) {
        return new ApiErrorResponse(errorMessage);
    }


}
