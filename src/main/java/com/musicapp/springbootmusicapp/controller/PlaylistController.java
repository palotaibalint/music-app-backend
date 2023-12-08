package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.PlaylistRepository;
import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.service.PlaylistService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongRepository songRepository;

    // Create a new playlist
    @PostMapping("/private/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam String username,@RequestParam String name,@RequestParam boolean generate) {
        Playlist createdPlaylist=new Playlist(username,name);
        playlistService.createPlaylist(createdPlaylist);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @GetMapping("/private/get-songs")
    public ResponseEntity<List<Song>> getAllSongsFromPlaylist(@RequestParam Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);

        if (playlist != null) {
            List<Song> songs = playlist.getSongs();
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/private/add-song")
    public ResponseEntity<Playlist> addSongToPlaylist(
            @RequestParam Long playlistId,
            @RequestParam Long songId
    ) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        Song song = songRepository.findById(songId).orElse(null);

        if (playlist != null && song != null) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
        }   

        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @GetMapping("/private/get-all")
    public ResponseEntity<List<Playlist>> getAllUserPlaylists(@RequestParam("username") String username) {
        return new ResponseEntity<>(playlistService.getAllUserPlaylists(username),HttpStatus.OK);
    }
}

