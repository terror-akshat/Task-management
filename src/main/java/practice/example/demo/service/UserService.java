package practice.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import practice.example.demo.Entity.User;
import practice.example.demo.Repository.UserRepository;
import practice.example.demo.dto.UserResponse;
import practice.example.demo.dto.UserRequest;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        User savedUser = userRepository.save(newUser);
        return convertToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).stream().map(this::convertToResponse).findFirst().orElse(null);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setTasks(user.getTasks());
        return response;
    }
}
