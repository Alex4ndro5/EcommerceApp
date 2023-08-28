package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.infy.EcommerceApp.model.User;
import com.infy.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    public UserController() {
    }

    @GetMapping({"/users"})
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        try {
            ArrayList<User> users = (ArrayList)this.userRepository.findAll();
            return users.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(users, HttpStatus.OK);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/users/{id}"})
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> userData = this.userRepository.findById(id);
        return userData.isPresent() ? new ResponseEntity((User)userData.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping({"/users"})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = (User)this.userRepository.save(new User(user.getUserName(), user.getUserPassword(), user.getUserBirthdate(), user.getUserEmail(), user.getUserAddress()));
            return new ResponseEntity(_user, HttpStatus.CREATED);
        } catch (Exception var3) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/users/{id}"})
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userData = this.userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = (User)userData.get();
            _user.setUserName(user.getUserName());
            _user.setUserPassword(user.getUserPassword());
            _user.setUserBirthdate(user.getUserBirthdate());
            _user.setUserEmail(user.getUserEmail());
            _user.setUserAddress(user.getUserAddress());
            return new ResponseEntity((User)this.userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/users/{id}"})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            this.userRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping({"/users"})
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            this.userRepository.deleteAll();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
