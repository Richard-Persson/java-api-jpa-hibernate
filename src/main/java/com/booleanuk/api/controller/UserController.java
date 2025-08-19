package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Integer id)
    {
        return this.userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User newUser = new User(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPhone());

        return new ResponseEntity<>(this.userRepository.save(newUser), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){

        User userToUpdate = userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPhone(user.getPhone());

        return new ResponseEntity<>(userRepository.save(userToUpdate),HttpStatus.CREATED);

    }



    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){

        User userToDelete = userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        userRepository.delete(userToDelete);
        return ResponseEntity.ok(userToDelete);
    }


}
