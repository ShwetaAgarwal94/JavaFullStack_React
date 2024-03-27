package com.project.fullstackbackend.controller;

import com.project.fullstackbackend.exception.UserNotFoundException;
import com.project.fullstackbackend.model.User;
import com.project.fullstackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(
                user -> {
                    user.setName(updatedUser.getName());
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable("id") Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully";
    }

}

