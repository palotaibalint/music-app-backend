package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.UserRepository;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Song> getSongsByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getSongs).orElse(Collections.emptyList());
    }

    @Transactional
    public User addOrUpdateUser(String userName, String userEmail) {
        return userRepository.findByUsername(userName)
                .orElseGet(() -> {
                    Optional<User> userOpt = userRepository.findByUsername(userName);
                    return userOpt.orElseGet(() -> userRepository.save(new User(userName, userEmail)));
                });
    }

}
