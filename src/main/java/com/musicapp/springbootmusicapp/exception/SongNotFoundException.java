package com.musicapp.springbootmusicapp.exception;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(String message) {
        super(message);
    }

    public SongNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SongNotFoundException(Long songId) {
        super("Song not found with id: " + songId);
    }
}
