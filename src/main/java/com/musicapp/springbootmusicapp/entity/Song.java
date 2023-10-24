package com.musicapp.springbootmusicapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="song")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="song_id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="artist")
    private String artist;

    @Column(name="album")
    private String album;

    @Column(name="duration")
    private int duration;
}
