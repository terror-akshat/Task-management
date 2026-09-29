package practice.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import practice.example.demo.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
