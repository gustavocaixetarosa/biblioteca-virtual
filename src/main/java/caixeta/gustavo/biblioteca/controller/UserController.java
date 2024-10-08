package caixeta.gustavo.biblioteca.controller;

import caixeta.gustavo.biblioteca.model.User;
import caixeta.gustavo.biblioteca.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.listAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User foundUser = userService.searchById(id);
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user){
        User userSaved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user){
        user.setId(id);
        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
