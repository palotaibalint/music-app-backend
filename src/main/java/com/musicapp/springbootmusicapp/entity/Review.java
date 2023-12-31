package com.musicapp.springbootmusicapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name="review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="user_name")
    private String userName;

    @Column(name="song_id")
    private long songId;

    @Column(name="date")
    @CreationTimestamp
    private Date date;

    @Column(name="rating")
    private double rating;

    @Column(name="review_title")
    private String reviewTitle;

    @Column(name="review_description")
    private String reviewDescription;

    public Review(){

    }

    public Review(long id, String userEmail, String userName, long songId, Date date, double rating, String reviewTitle, String reviewDescription) {
        this.id = id;
        this.userEmail = userEmail;
        this.userName = userName;
        this.songId = songId;
        this.date = date;
        this.rating = rating;
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
    }
}
