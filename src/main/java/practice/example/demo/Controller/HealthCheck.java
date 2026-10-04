package practice.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health")
    public String health() {
        return "Server is healthy!! Now let start the coding.";
    }
}