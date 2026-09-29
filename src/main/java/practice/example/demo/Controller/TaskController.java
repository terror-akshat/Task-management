package practice.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import practice.example.demo.Entity.Task;
import practice.example.demo.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "Task with ID " + id + " has been deleted.";
    }
}