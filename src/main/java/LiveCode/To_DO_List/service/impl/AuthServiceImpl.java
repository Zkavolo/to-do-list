package LiveCode.To_DO_List.service.impl;

import LiveCode.To_DO_List.model.Enum.Role;
import LiveCode.To_DO_List.model.UserEntity;
import LiveCode.To_DO_List.repository.UserRepository;
import LiveCode.To_DO_List.security.JwtService;
import LiveCode.To_DO_List.service.AuthService;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterResponse;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RefreshToken;
import LiveCode.To_DO_List.util.DTO.AuthDTO.AuthenticationResponse;
import LiveCode.To_DO_List.util.DTO.AuthDTO.RegisterRequest;
import LiveCode.To_DO_List.util.DTO.AuthDTO.AuthenticationRequest;
import LiveCode.To_DO_List.util.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("regisPass");
        }

        if (userRepository.findByName(request.getUsername()).isPresent()) {
            throw new RuntimeException("regisUser");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        var user = UserEntity.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .createdAt(new Date())
                .build();

        UserEntity newUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("name"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshToken refreshToken) {
        if (jwtService.validateToken(refreshToken.getRefreshToken())) {
            String email = jwtService.extractUsername(refreshToken.getRefreshToken());
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
