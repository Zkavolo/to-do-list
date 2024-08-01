package LiveCode.To_DO_List.controller;

import LiveCode.To_DO_List.service.AuthService;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RefreshToken;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.AuthenticationRequest;
import LiveCode.To_DO_List.util.Response.ErrorMapper;
import LiveCode.To_DO_List.util.Response.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Validated
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, Errors errors){
        if(errors.hasErrors()){
            ErrorResponse<?> errorResponseData = ErrorMapper.renderErrors("Something wrong whilst registering",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
        }
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authrequest, Errors errors){
        if(errors.hasErrors()){
            ErrorResponse<?> errorResponseData = ErrorMapper.renderErrors("Something wrong whilst logging in",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
        }
        return ResponseEntity.ok(authService.authenticate(authrequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
