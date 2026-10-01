package practice.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import practice.example.demo.Entity.Task;
import practice.example.demo.Entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByUserIdAndStatus(Long userId, TaskStatus status);
}