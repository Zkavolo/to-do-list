package LiveCode.To_DO_List.controller;

import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.service.TodoService;
import LiveCode.To_DO_List.util.DTO.TodoDTO.StatusDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.UpdateTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.RequestTodoDTO;
import LiveCode.To_DO_List.util.Response.PageResponseWrapper;
import LiveCode.To_DO_List.util.Response.Response;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody RequestTodoDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                todoService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String status)
    {
        Page<ResponseTodoDTO> result = todoService.getAll(pageable,status);
        PageResponseWrapper<ResponseTodoDTO> response = new PageResponseWrapper<>(result);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                todoService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody UpdateTodoDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(
                todoService.updateById(id,request)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody StatusDTO status){
        return ResponseEntity.status(HttpStatus.OK).body(
                todoService.updateStatus(status, id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

