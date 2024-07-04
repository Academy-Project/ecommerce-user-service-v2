package userstore.userservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import userstore.userservice.model.UserModel;
import userstore.userservice.repository.RepositoryUser;
import userstore.userservice.responses.ResponseHandler;

import java.util.Collections;
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
            return ResponseHandler.errorResponse(
                    Collections.singletonList("User not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserModel user) {
        try {
            UserModel newUser = repositoryUser.save(user);
            return ResponseHandler.generateResponse("User added successfully", newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.errorResponse(
                    Collections.singletonList("Failed to add user: "),
                    HttpStatus.NOT_FOUND);
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
            return ResponseHandler.generateResponse("User updated successfully", existingUser, HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(
                    Collections.singletonList("User not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        Optional<UserModel> userOptional = repositoryUser.findById(userId);
        if (userOptional.isPresent()) {
            repositoryUser.deleteById(userId);
            return ResponseHandler.successResponse("User deleted successfully", HttpStatus.OK);
        } else {
            return ResponseHandler.errorResponse(
                    Collections.singletonList("User not found"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
