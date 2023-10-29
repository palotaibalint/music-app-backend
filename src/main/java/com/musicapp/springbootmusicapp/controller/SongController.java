package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            songs = songService.findByTitleContaining(title,pageable);
        }
        else{
            songs = songService.getAllSongs(pageable);
        }

        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
}
