package practice.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import practice.example.demo.Entity.TaskStatus;
import practice.example.demo.dto.TaskRequest;
import practice.example.demo.dto.TaskResponse;
import practice.example.demo.service.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task) {
        TaskResponse createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTaskByUserId(@PathVariable Long userId) {
        return taskService.getTaskByUserId(userId);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public List<TaskResponse> getTaskByUserIdAndStatus(@PathVariable Long userId, @PathVariable TaskStatus status) {
        return taskService.getTaskByUserIdAndStatus(userId, status);
    }

    @GetMapping("/status/{status}")
    public List<TaskResponse> getTaskByStatus(@PathVariable TaskStatus status) {
        return taskService.getTaskByStatus(status);
    }

    @DeleteMapping("/{id}")
    public String deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "Task with ID " + id + " has been deleted.";
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }
}