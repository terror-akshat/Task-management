package practice.example.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import practice.example.demo.Entity.Task;

@Data
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}