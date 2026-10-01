package practice.example.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTaskByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTaskByUserId(userId));
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTaskByUserIdAndStatus(@PathVariable Long userId, @PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.getTaskByUserIdAndStatus(userId, status));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTaskByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.getTaskByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok("Task with ID " + id + " has been deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updatedTask) {
        TaskResponse updatedTaskResponse = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(updatedTaskResponse);
    }
}