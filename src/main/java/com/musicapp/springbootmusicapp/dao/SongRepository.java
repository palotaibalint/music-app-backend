package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> findByTitleContaining(String title, Pageable pageable);

    Page<Song> findByArtistContaining(String artist, Pageable pageable);

    Page<Song> findByAlbumContaining(String album, Pageable pageable);

    Page<Song> findAll(Pageable pageable);
}

