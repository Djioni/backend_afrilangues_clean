package api.afrilangue.helper;


import api.afrilangue.dto.request.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.badRequest;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
public class ResponseUtils {

    private ResponseUtils() {
        new UnsupportedOperationException("Cant not instantiate this");
    }

    public static ResponseEntity error(String message) {
        return badRequest().body(message);
    }

    public static ResponseEntity<ApiErrorResponse> apiErrorResponse(HttpStatus status, Exception error) {
        return ResponseEntity.status(status).body(ApiErrorResponse.from(error.getMessage()));
    }






}
