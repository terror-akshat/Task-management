package practice.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity 
@Data 
@Table(name="tasks")
public class Task {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
}
