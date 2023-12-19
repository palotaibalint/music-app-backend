package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.dto.SongDTO;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import com.musicapp.springbootmusicapp.service.SongService;
import com.musicapp.springbootmusicapp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class SongController {

    @Autowired
    private final UserService userService;

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    public SongController(UserService userService, SongService songService, SongRepository songRepository) {
        this.userService = userService;
        this.songService = songService;
        this.songRepository = songRepository;
    }

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

    @GetMapping("public/most-reviews")
    public ResponseEntity<List<Song>> getSongsWithMostReviews() {
        List<Song> songs = songService.findSongsWithMostReviews();
        if (songs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("public/most-on-playlists")
    public ResponseEntity<List<Song>> getPopularPlaylistSongs() {
        List<Song> songs = songService.findPopularPlaylistSongs();
        if (songs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/private/upload")
    public ResponseEntity<?> uploadSong(@RequestBody SongDTO songDTO) {
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setArtist(songDTO.getArtist());
        song.setAlbum(songDTO.getAlbum());
        song.setDuration(songDTO.getDuration());
        song.setImg(songDTO.getImg());
        song.setLink(songDTO.getLink());
        song.setGenres(songDTO.getGenres().split(","));
        song.setMusicUser(userService.findByUsername(songDTO.getOwnerUsername()));

        songRepository.save(song);

        return ResponseEntity.ok().build();
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

    @GetMapping("/public/incrementClicks")
    public ResponseEntity<Integer> incrementClicks(@RequestParam("id") Long id) {
        Integer updatedClicks = songService.incrementSongClicks(id);
        return new ResponseEntity<>(updatedClicks, HttpStatus.OK);
    }

    @GetMapping("/private/song-owner")
    public ResponseEntity<String> getSongOwnerUsername(@RequestParam("id") Long songId) {
        Optional<Song> songOpt = songService.getSongById(songId);

        if (songOpt.isPresent()) {
            Song song = songOpt.get();
            User owner = song.getMusicUser();

            if (owner != null) {
                return new ResponseEntity<>(owner.getUsername(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No owner associated with this song", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

}
