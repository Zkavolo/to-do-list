package LiveCode.To_DO_List.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        if(ex.getMessage().contains("User")){
            message = "User not found";
        } else if(ex.getMessage().contains("Task")){
            message = "Task not found";
        } else if(ex.getMessage().contains("regisPass")){
            message = "Email already taken";
        } else if(ex.getMessage().contains("Name")){
            message = "Wrong username or password";
        } else if (ex.getMessage().contains("regisUser")) {
            message = "Username already taken";
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
