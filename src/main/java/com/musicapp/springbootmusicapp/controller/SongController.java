package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/search")
    public ResponseEntity<Page<Song>> getSongsByTitle(@RequestParam("title") String title,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs;
        if (title != null && !title.isEmpty()) {
            songs = songService.findByTitleIgnoreCaseContaining(title,pageable);
        }
        else{
            songs = songService.getAllSongs(pageable);
        }

        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/top-songs")
    public ResponseEntity<List<Song>> getTopSongs() {
        return new ResponseEntity<>(songService.getTopFiveSongsByClicks(),HttpStatus.OK);
    }

    @PostMapping("/addSong")
    public ResponseEntity<Song> addSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("album") String album,
            @RequestParam("duration") String duration,
            @RequestParam("img") String img
    ) {
        Song createdSong = new Song(title, artist, album, duration, img);
        songService.addSong(createdSong);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }
}
