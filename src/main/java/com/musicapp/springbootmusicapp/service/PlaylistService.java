package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.PlaylistRepository;
import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SongRepository songRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllUserPlaylists(String user) {
        return playlistRepository.findByUser(user);
    }

    @Transactional
    public void removeSongFromPlaylist(Long songId, Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(/* ... */);
        Song song = songRepository.findById(songId).orElseThrow(/* ... */);

        playlist.getSongs().remove(song);
        song.getPlaylists().remove(playlist);

        playlistRepository.save(playlist);
        songRepository.save(song);
    }

    public void deletePlaylistById(Long id) {
        playlistRepository.deleteById(id);
    }

    @Transactional
    public Optional<List<Song>> getSongsByPlaylistId(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .map(Playlist::getSongs);
    }
}

