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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/public/search/title")
    public ResponseEntity<Page<Song>> getSongsByTitle(@RequestParam(name = "title", defaultValue = "") String title,
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

    @GetMapping("/public/search/artist")
    public ResponseEntity<Page<Song>> getSongsByArtist(@RequestParam(name = "artist", defaultValue = "") String artist,
                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs;
        if (artist != null && !artist.isEmpty()) {
            songs = songService.findByArtistIgnoreCaseContaining(artist,pageable);
        }
        else{
            songs = songService.getAllSongs(pageable);
        }

        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/public/search/album")
    public ResponseEntity<Page<Song>> getSongsByAlbum(@RequestParam(name = "album", defaultValue = "") String album,
                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs;
        if (album != null && !album.isEmpty()) {
            songs = songService.findByAlbumIgnoreCaseContaining(album,pageable);
        }
        else{
            songs = songService.getAllSongs(pageable);
        }

        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/public/top-songs")
    public ResponseEntity<List<Song>> getTopSongs() {
        return new ResponseEntity<>(songService.getTopFiveSongsByClicks(),HttpStatus.OK);
    }

    @GetMapping("/public/with-id")
    public ResponseEntity<Optional<Song>> getSongById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(songService.getSongById(id),HttpStatus.OK);
    }

    @PostMapping("/private/addSong")
    public ResponseEntity<Song> addSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("album") String album,
            @RequestParam("duration") String duration,
            @RequestParam("genres") String[] genres,
            @RequestParam("img") String img,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Song createdSong = new Song(title, artist, album, duration, img,genres, file.getBytes());
        songService.addSong(createdSong);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }
}
