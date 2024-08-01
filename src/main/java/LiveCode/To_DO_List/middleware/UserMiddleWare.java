package LiveCode.To_DO_List.middleware;

import LiveCode.To_DO_List.exception.ValidateException;
import LiveCode.To_DO_List.model.Enum.Role;
import LiveCode.To_DO_List.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMiddleWare {
    private final UserRepository userRepository;

    public static void isAdmin(Role role) {
        if (role != Role.Admin) throw new ValidateException("Invalid Role!");
    }

    public static void isDoctor(Role role) {
        if (role != Role.User) throw new ValidateException("Invalid Role!");
    }
}
