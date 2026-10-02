package practice.example.demo.Repository;

import practice.example.demo.Entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}

// That will give you access to the following methods for free:
// save()findById()findAll()deleteById