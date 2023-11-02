package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Page<Song> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    public Page<Song> findByTitleIgnoreCaseContaining(String title, Pageable pageable) {
        return songRepository.findByTitleIgnoreCaseContaining(title, pageable);
    }

    public List<Song> getTopFiveSongsByClicks() {
        return songRepository.findTop5ByOrderByClicksDesc();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }
}
