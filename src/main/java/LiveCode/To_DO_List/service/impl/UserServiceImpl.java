package LiveCode.To_DO_List.service.impl;

import LiveCode.To_DO_List.exception.ValidateException;
import LiveCode.To_DO_List.model.Enum.Role;
import LiveCode.To_DO_List.model.Todo;
import LiveCode.To_DO_List.model.UserEntity;
import LiveCode.To_DO_List.repository.UserRepository;
import LiveCode.To_DO_List.service.UserService;
import LiveCode.To_DO_List.util.DTO.AuthDTO.ChangeRoleRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<UserDetailsResponse> getAllUsers(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.User) {
            throw new ValidateException("Invalid Role!");
        }

        Page<UserEntity> userPaged = userRepository.findAll(pageable);
        List<UserDetailsResponse> result = userPaged.stream().
                map(page -> new UserDetailsResponse(page.getId(), page.getName(),
                        page.getEmail(), page.getRole(), page.getCreatedAt()))
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, userPaged.getTotalElements());
    }

    @Override
    public UserDetailsResponse getOneUser(Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.User) {
            throw new ValidateException("Invalid Role!");
        }

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));

        return UserDetailsResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }

    @Override
    public UserDetailsResponse updateUserRole(ChangeRoleRequest request, Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("user"));

        if (user.getRole() == Role.User) {
            throw new ValidateException("Invalid Role!");
        }

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));

        if (request.getRole().equalsIgnoreCase("Admin") || request.getRole().equalsIgnoreCase("User")) {
            userEntity.setRole(Role.valueOf(request.getRole()));
        } else {
            throw new RuntimeException("Role not found");
        }

        userRepository.save(userEntity);

        return UserDetailsResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }
}
