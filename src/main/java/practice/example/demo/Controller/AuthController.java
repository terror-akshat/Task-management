package practice.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import practice.example.demo.Entity.User;
import practice.example.demo.dto.AuthResponse;
import practice.example.demo.dto.LoginRequest;
import practice.example.demo.dto.LoginResponse;
import practice.example.demo.dto.UserRequest;
import practice.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> Register(@Valid @RequestBody UserRequest user) {
        User newUser = authService.Resgister(user);
        AuthResponse response = new AuthResponse(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
