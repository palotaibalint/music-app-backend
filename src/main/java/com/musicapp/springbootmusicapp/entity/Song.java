package com.musicapp.springbootmusicapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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

    @Column(name="img")
    private String img;

    @Column(name="genres")
    private String[] genres;

    @Column(name="duration")
    private String duration;

    @Column(name = "clicks", columnDefinition = "int default 0")
    private int clicks;

    @Column(name="link")
    private String link;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @JsonBackReference
    private List<Playlist> playlists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User musicUser;

    public Song(){

    }

    public Song(String title, String artist, String album, String duration, String img,String[] genres,User owner,String link) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.img = img;
        this.genres=genres;
        this.clicks=0;
        this.link=link;
        this.musicUser=owner;
    }

    public Long getSong_id() {
        return song_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public User getMusicUser() {
        return musicUser;
    }

    public void setMusicUser(User musicUser) {
        this.musicUser = musicUser;
    }
}
