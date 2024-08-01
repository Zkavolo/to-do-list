package LiveCode.To_DO_List.util.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WebResponse<T> {
    private String status;
    private T data;
}

