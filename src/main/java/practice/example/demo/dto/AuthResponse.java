package practice.example.demo.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private Long id;
    private String name;
    private String email;

    public AuthResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}