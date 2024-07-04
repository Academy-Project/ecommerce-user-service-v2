package userstore.userservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import userstore.userservice.model.UserModel;
import userstore.userservice.repository.RepositoryUser;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RepositoryUser repositoryUser;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = (List<UserModel>) repositoryUser.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        Optional<UserModel> userOptional = repositoryUser.findById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserModel user) {
        try {
            UserModel newUser = repositoryUser.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody UserModel newUserDetails) {
        Optional<UserModel> userOptional = repositoryUser.findById(userId);
        if (userOptional.isPresent()) {
            UserModel existingUser = userOptional.get();
            existingUser.setUsername(newUserDetails.getUsername());
            existingUser.setEmail(newUserDetails.getEmail());
            existingUser.setPassword(newUserDetails.getPassword());
            repositoryUser.save(existingUser);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        Optional<UserModel> userOptional = repositoryUser.findById(userId);
        if (userOptional.isPresent()) {
            repositoryUser.deleteById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
