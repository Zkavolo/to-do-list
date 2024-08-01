package LiveCode.To_DO_List.service;

import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterResponse;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RefreshToken;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.AuthenticationRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refreshToken(RefreshToken refreshToken);
}
