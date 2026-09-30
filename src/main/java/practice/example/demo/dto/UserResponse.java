package practice.example.demo.dto;

import java.util.List;

import lombok.Data;
import practice.example.demo.Entity.Task;


@Data 
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Task> tasks;
}
