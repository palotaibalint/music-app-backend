package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.PlaylistRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        // Implement the logic to create and save the playlist.
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
}

