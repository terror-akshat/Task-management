package practice.example.demo.dto;

import lombok.Data;
import practice.example.demo.Entity.TaskStatus;


@Data 
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

}
