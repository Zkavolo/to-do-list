package LiveCode.To_DO_List.util.Response;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ErrorMapper {
    public static ErrorResponse<?> renderErrors(String message, Errors errors) {
        ErrorResponse<?> responseError = new ErrorResponse<>();

        for (ObjectError error : errors.getAllErrors()) {
            responseError.getErrors().add(error.getDefaultMessage());
        }
        responseError.setMessage(message);

        return responseError;
    }
}
