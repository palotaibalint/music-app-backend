package com.musicapp.springbootmusicapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="song")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="song_id")
    private Long song_id;

    @Column(name="title")
    private String title;

    @Column(name="artist")
    private String artist;

    @Column(name="album")
    private String album;

    @Column(name="duration")
    private String duration;

    @Column(name="img")
    private String img;

    @Column(name = "clicks", columnDefinition = "int default 0")
    private int clicks;

    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists;

    public Song(){

    }

    public Song(String title, String artist, String album, String duration, String img) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.img = img;
        this.clicks=0;
    }


}
