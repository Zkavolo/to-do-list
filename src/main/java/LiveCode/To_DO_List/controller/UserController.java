package LiveCode.To_DO_List.controller;

import LiveCode.To_DO_List.service.UserService;
import LiveCode.To_DO_List.util.DTO.AuthDTO.ChangeRoleRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.UserDetailsResponse;
import LiveCode.To_DO_List.util.Response.PageResponseWrapper;
import LiveCode.To_DO_List.util.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getOneUser(id)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable)
    {
        Page<UserDetailsResponse> result = userService.getAllUsers(pageable);
        PageResponseWrapper<UserDetailsResponse> response = new PageResponseWrapper<>(result);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@RequestBody ChangeRoleRequest request,@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.updateUserRole(request,id)
        );
    }



}
