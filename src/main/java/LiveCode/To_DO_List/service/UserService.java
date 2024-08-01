package LiveCode.To_DO_List.service;

import LiveCode.To_DO_List.model.UserEntity;
import LiveCode.To_DO_List.util.DTO.AuthDTO.ChangeRoleRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.UserDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDetailsResponse> getAllUsers(Pageable pageable);
    UserDetailsResponse getOneUser(Integer id);
    UserDetailsResponse updateUserRole(ChangeRoleRequest request, Integer id);
}
