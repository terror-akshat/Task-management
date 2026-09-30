package practice.example.demo.Controller;

import org.springframework.web.bind.annotation.*;

import practice.example.demo.dto.UserRequest;
import practice.example.demo.dto.UserResponse;
import practice.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest user) {
        return userService.createUser(user);
    }

    @GetMapping("/get-all")
    public String getAllUsers() {
        return userService.getAllUsers().toString();
    }

    @GetMapping("/get/{id}")
    public String getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        if (user != null) {
            return user.toString();
        } else {
            return "User not found";
        }
    }
}