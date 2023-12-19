package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.MessageRepository;
import com.musicapp.springbootmusicapp.dao.UserRepository;
import com.musicapp.springbootmusicapp.entity.Message;
import com.musicapp.springbootmusicapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        return messageRepository.findMessagesBetweenUsers(user1, user2);
    }

    public List<User> getUsersWithMessages(User user) {
        return userRepository.findDistinctUsersWithMessages(user.getUser_id());
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}

