package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.ReviewRepository;
import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Song;
import com.musicapp.springbootmusicapp.exception.SongNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository songRepository;

    private final ReviewRepository reviewRepository;

    public SongService(SongRepository songRepository, ReviewRepository reviewRepository) {
        this.songRepository = songRepository;
        this.reviewRepository = reviewRepository;
    }

    public Page<Song> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    public Page<Song> findByTitleIgnoreCaseContaining(String title, Pageable pageable) {
        return songRepository.findByTitleIgnoreCaseContaining(title, pageable);
    }

    public Integer incrementSongClicks(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new SongNotFoundException("Song not found"));
        song.setClicks(song.getClicks() + 1);
        songRepository.save(song);
        return song.getClicks();
    }

    public List<Song> findSongsWithMostReviews() {
        List<Object[]> reviewsCount = reviewRepository.countReviewsPerSong();
        List<Long> songIds = reviewsCount.stream()
                .map(row -> (Long) row[0])
                .limit(5)
                .collect(Collectors.toList());

        return songRepository.findAllById(songIds);
    }

    public Page<Song> findByArtistIgnoreCaseContaining(String artist, Pageable pageable) {
        return songRepository.findByArtistIgnoreCaseContaining(artist, pageable);
    }

    public List<Song> findPopularPlaylistSongs() {
        return songRepository.findTopSongsInPlaylists();
    }

    public Page<Song> findByAlbumIgnoreCaseContaining(String album, Pageable pageable) {
        return songRepository.findByAlbumIgnoreCaseContaining(album, pageable);
    }

    @Transactional
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    public List<Song> getTopFiveSongsByClicks() {
        return songRepository.findTop5ByOrderByClicksDesc();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }
}
