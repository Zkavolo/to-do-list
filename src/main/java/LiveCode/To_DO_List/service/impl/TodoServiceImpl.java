package LiveCode.To_DO_List.service.impl;

import LiveCode.To_DO_List.exception.ValidateException;
import LiveCode.To_DO_List.model.Enum.Role;
import LiveCode.To_DO_List.model.Enum.TodoStatus;
import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.model.UserEntity;
import LiveCode.To_DO_List.repository.TodoRepository;
import LiveCode.To_DO_List.repository.UserRepository;
import LiveCode.To_DO_List.service.TodoService;
import LiveCode.To_DO_List.util.DTO.TodoDTO.StatusDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.UpdateTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.RequestTodoDTO;
import LiveCode.To_DO_List.util.Specification.TodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todorepository;

    @Override
    public ResponseTodoDTO create(RequestTodoDTO request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.Admin) {
            throw new ValidateException("Invalid Role!");
        }

        Todo newTodo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(String.valueOf(TodoStatus.PENDING))
                .createdAt(new Date())
                .user(user)
                .build();

        todorepository.save(newTodo);

        ResponseTodoDTO result = ResponseTodoDTO.builder()
                .id(newTodo.getId())
                .title(newTodo.getTitle())
                .description(newTodo.getDescription())
                .dueDate(newTodo.getDueDate())
                .status(newTodo.getStatus())
                .createdAt(newTodo.getCreatedAt())
                .build();

        return result;
    }

    @Override
    public Page<ResponseTodoDTO> getAll(Pageable pageable, String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.Admin) {
            throw new ValidateException("Invalid Role!");
        }

        Specification<Todo> spec = TodoSpecification.getSpecification(status);

        Page<Todo> todoPaged = todorepository.findAll(spec, pageable);
        List<ResponseTodoDTO> newList = todoPaged.stream()
                .map(todo -> new ResponseTodoDTO(
                        todo.getId(), todo.getTitle(), todo.getDescription(),
                        todo.getDueDate(), todo.getStatus(), todo.getCreatedAt()))
                .collect(Collectors.toList());

        return new PageImpl<>(newList,pageable, todoPaged.getNumberOfElements());
    }

    @Override
    public Todo getById(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.Admin) {
            throw new ValidateException("Invalid Role!");
        }

        return todorepository.findById(id).orElseThrow(()-> new RuntimeException("Todo"));
    }

    @Override
    public ResponseTodoDTO updateById(Integer id, UpdateTodoDTO request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.Admin) {
            throw new ValidateException("Invalid Role!");
        }

        Todo oldTodo = todorepository.findById(id).orElseThrow(()-> new RuntimeException("Todo"));

        oldTodo.setTitle(request.getTitle());
        oldTodo.setDescription(request.getDescription());
        oldTodo.setDueDate(request.getDueDate());
        oldTodo.setStatus(request.getStatus());

        Todo result = todorepository.save(oldTodo);

        ResponseTodoDTO responseTodoDTO = ResponseTodoDTO.builder()
                .id(result.getId())
                .description(result.getDescription())
                .title(result.getTitle())
                .dueDate(result.getDueDate())
                .status(result.getStatus())
                .createdAt(result.getCreatedAt())
                .build();

        return responseTodoDTO;
    }

    @Override
    public ResponseTodoDTO updateStatus(StatusDTO status, Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        Todo oldTodo = todorepository.findById(id).orElseThrow(()-> new RuntimeException("Todo"));

        if (user.getRole() == Role.Admin) {
            throw new ValidateException("Invalid Role!");
        }

        if (status.getStatus().equalsIgnoreCase("COMPLETED") || status.getStatus().equalsIgnoreCase("IN_PROGRESS")) {
            oldTodo.setStatus(status.getStatus());
        } else {
            throw new RuntimeException("Invalid Status");
        }

        Todo result = todorepository.save(oldTodo);

        ResponseTodoDTO responseTodoDTO = ResponseTodoDTO.builder()
                .id(result.getId())
                .description(result.getDescription())
                .title(result.getTitle())
                .dueDate(result.getDueDate())
                .status(result.getStatus())
                .createdAt(result.getCreatedAt())
                .build();

        return responseTodoDTO;
    }

    @Override
    public void deleteById(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        Todo oldTodo = todorepository.findById(id).orElseThrow(()-> new RuntimeException("Todo"));

        todorepository.delete(oldTodo);
    }
}
