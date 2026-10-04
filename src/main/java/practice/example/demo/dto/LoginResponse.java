package practice.example.demo.dto;

import lombok.Data;
import practice.example.demo.Entity.UserRole;

@Data
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String token;
    private UserRole role;
}
