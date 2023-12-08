package com.musicapp.springbootmusicapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="text")
    private String text;

    @Column(name="review_id")
    private Long reviewId;

    public Comment(){

    }

    public Comment(String userName, String text, Long reviewId){
        this.userName=userName;
        this.text=text;
        this.reviewId=reviewId;
    }
}
