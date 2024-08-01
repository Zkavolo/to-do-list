package LiveCode.To_DO_List.util.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse<T> {
    private List<String> errors = new ArrayList<>();
    private String message;
}
