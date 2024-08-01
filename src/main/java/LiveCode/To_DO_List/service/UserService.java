package LiveCode.To_DO_List.service;

import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.model.UserEntity;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.ChangeRoleRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.UserDetailsResponse;
import LiveCode.To_DO_List.util.DTO.TodoDTO.ResponseTodoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDetailsResponse> getAllUsers(Pageable pageable);
    UserDetailsResponse getOneUser(Integer id);
    UserDetailsResponse updateUserRole(ChangeRoleRequest request, Integer id);
    UserDetailsResponse createSuperAdminUser(RegisterRequest request);
    Page<ResponseTodoDTO> getAllTodos(Pageable pageable, String status);
    Todo getOneTodo(Integer id);
}
