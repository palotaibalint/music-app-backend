package com.musicapp.springbootmusicapp.dto;

public class SongDTO {
    private String link;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String img;
    private String genres;
    private String ownerUsername;

    public SongDTO(){

    }

    public SongDTO(String title, String artist, String album, String duration, String img, String link, String genres, String ownerUsername) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.link=link;
        this.img = img;
        this.genres = genres;
        this.ownerUsername = ownerUsername;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}