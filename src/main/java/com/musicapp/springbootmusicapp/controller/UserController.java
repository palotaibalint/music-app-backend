package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.UserRepository;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import com.musicapp.springbootmusicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/private/user")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/private/update-profile-pic")
    public ResponseEntity<User> updateUserProfilePic(@RequestParam("username") String username, @RequestParam("url") String profilePicUrl) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setImg(profilePicUrl);
        User updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("private/add")
    public ResponseEntity<User> addUser(@RequestParam String userName, @RequestParam String userEmail) {
        User savedUser = userService.addOrUpdateUser(userName,userEmail);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping("/private/check")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam String userEmail) {
        User user = userService.findByEmail(userEmail);
        boolean userExists = user != null;
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }

    @GetMapping("/private/user-songs")
    public ResponseEntity<List<Song>> getSongsByUser(@RequestParam String username) {
        List<Song> songs = userService.getSongsByUser(username);
        if (songs != null && !songs.isEmpty()) {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            if(songs != null) {
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

}