package com.musicapp.springbootmusicapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    public Message(){

    }

    public Message(String content,User sender,User receiver){
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }
}

