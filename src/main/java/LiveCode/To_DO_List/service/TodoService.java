package LiveCode.To_DO_List.service;

import LiveCode.To_DO_List.model.Todo;

import LiveCode.To_DO_List.util.DTO.TodoDTO.StatusDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.UpdateTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.RequestTodoDTO;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface TodoService {
    ResponseTodoDTO create(RequestTodoDTO request);
    Page<ResponseTodoDTO> getAll(Pageable pageable, String status);
    Todo getById(Integer id);
    ResponseTodoDTO updateById(Integer id, UpdateTodoDTO request);
    ResponseTodoDTO updateStatus(StatusDTO status, Integer id);
    void deleteById(Integer id);
}
