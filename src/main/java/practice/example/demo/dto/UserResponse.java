package practice.example.demo.dto;

import java.util.List;

import lombok.Data;
import practice.example.demo.Entity.Task;
import practice.example.demo.Entity.UserRole;


@Data 
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private List<Task> tasks;
}
