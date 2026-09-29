package practice.example.demo.Repository;

import practice.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}


// That will give you access to the following methods for free:
// save()findById()findAll()deleteById()existsById()