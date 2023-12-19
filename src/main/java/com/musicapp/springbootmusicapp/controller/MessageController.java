package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.entity.Message;
import com.musicapp.springbootmusicapp.entity.User;
import com.musicapp.springbootmusicapp.service.MessageService;
import com.musicapp.springbootmusicapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }



    @GetMapping("/private/between")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@RequestParam String username1, @RequestParam String username2) {
        User user1 = userService.findByUsername(username1);
        User user2 = userService.findByUsername(username2);

        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }

        List<Message> messages = messageService.getMessagesBetweenUsers(user1, user2);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/private/users-with-messages")
    public ResponseEntity<List<User>> getUsersWithMessages(@RequestParam String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<User> users = messageService.getUsersWithMessages(user);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/private/add")
    public ResponseEntity<Message> addMessage(@RequestParam String sender, @RequestParam String receiver, @RequestParam String content) {
        User senderUser = userService.findByUsername(sender);
        User receiverUser = userService.findByUsername(receiver);

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Message message = new Message(content, senderUser, receiverUser);
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

}
