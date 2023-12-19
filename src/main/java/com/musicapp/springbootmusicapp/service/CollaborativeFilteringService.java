package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.PlaylistRepository;
import com.musicapp.springbootmusicapp.entity.Playlist;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollaborativeFilteringService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Song> recommendSongsForUser(User user) {
        Map<Song, Integer> songFrequency = new HashMap<>();

        for (Playlist playlist : playlistRepository.findAll()) {
            for (Song song : playlist.getSongs()) {
                songFrequency.put(song, songFrequency.getOrDefault(song, 0) + 1);
            }
        }

        return songFrequency.entrySet().stream()
                .filter(entry -> !user.getSongs().contains(entry.getKey()))
                .sorted(Map.Entry.<Song, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
