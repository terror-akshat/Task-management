package practice.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import practice.example.demo.Entity.Task;
import practice.example.demo.Exception.TaskNotFoundException;
import practice.example.demo.Repository.TaskRepository;
import practice.example.demo.dto.TaskRequest;
import practice.example.demo.dto.TaskResponse;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        Task savedTask = taskRepository.save(newTask);

        return convertToTaskResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> {
                    TaskResponse response = new TaskResponse();
                    response.setId(task.getId());
                    response.setTitle(task.getTitle());
                    response.setDescription(task.getDescription());
                    response.setStatus(task.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        return convertToTaskResponse(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            return convertToTaskResponse(taskRepository.save(task));
        }).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    }

    public TaskResponse convertToTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        return response;
    }
}
