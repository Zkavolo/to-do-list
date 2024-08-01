package LiveCode.To_DO_List.util.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


public class TodoDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestTodoDTO {
        @NotBlank(message = "title cannot be blank")
        private String title;

        @NotBlank(message = "description cannot be blank")
        private String description;

        @NotNull(message = "due Date cannot be blank")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date dueDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseTodoDTO {
        private Integer id;
        private String title;
        private String description;
        private Date dueDate;
        private String status;
        private Date createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateTodoDTO {
        private String title;
        private String description;
        private Date dueDate;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusDTO{
        private String status;
    }
}