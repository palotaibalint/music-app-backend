package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.PlaylistRepository;
import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import com.musicapp.springbootmusicapp.service.CollaborativeFilteringService;
import com.musicapp.springbootmusicapp.service.PlaylistService;
import com.musicapp.springbootmusicapp.service.ReviewService;
import com.musicapp.springbootmusicapp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class PlaylistController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private CollaborativeFilteringService collaborativeFilteringService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongRepository songRepository;

    @PostMapping("/private/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam String username,
                                                   @RequestParam String name,
                                                   @RequestParam boolean generate,
                                                   @RequestParam boolean popular,
                                                   @RequestParam String genre) {
        User user = userService.findByUsername(username);
        if (user != null) {
            Playlist createdPlaylist = new Playlist(username, name);
            if (generate) {
                List<Song> recommendedSongs = collaborativeFilteringService.recommendSongsForUser(user);
                if (popular) {
                    int popularityThreshold = 100;
                    recommendedSongs = recommendedSongs.stream()
                            .filter(song -> song.getClicks() >= popularityThreshold)
                            .collect(Collectors.toList());
                }
                if (!genre.isEmpty()) {
                    recommendedSongs = recommendedSongs.stream()
                            .filter(song -> Arrays.asList(song.getGenres()).contains(genre))
                            .collect(Collectors.toList());
                }
                recommendedSongs = recommendedSongs.stream()
                        .limit(15)
                        .collect(Collectors.toList());

                createdPlaylist.setSongs(recommendedSongs);
            }
            playlistService.createPlaylist(createdPlaylist);
            return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/private/delete_song")
    public ResponseEntity<Void> deleteSongFromPlaylist(@RequestParam Long song_id,@RequestParam Long playlist_id) {
        try {
            playlistService.removeSongFromPlaylist(song_id,playlist_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewService.ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<Void> deletePlaylist(@RequestParam Long id) {
        try {
            playlistService.deletePlaylistById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewService.ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/private/get-songs")
    public ResponseEntity<List<Song>> getAllSongsFromPlaylist(@RequestParam Long playlistId) {
        return playlistService.getSongsByPlaylistId(playlistId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/private/add-song")
    @Transactional
    public ResponseEntity<Playlist> addSongToPlaylist(
            @RequestParam Long playlistId,
            @RequestParam Long songId
    ) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(playlistId);
        Optional<Song> songOpt = songRepository.findById(songId);

        if (playlistOpt.isPresent() && songOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            Song song = songOpt.get();

            if (!playlist.getSongs().contains(song)) {
                playlist.getSongs().add(song);
                playlistRepository.save(playlist);
                return ResponseEntity.ok(playlist);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/private/get-all")
    public ResponseEntity<List<Playlist>> getAllUserPlaylists(@RequestParam("username") String username) {
        return new ResponseEntity<>(playlistService.getAllUserPlaylists(username),HttpStatus.OK);
    }
}

