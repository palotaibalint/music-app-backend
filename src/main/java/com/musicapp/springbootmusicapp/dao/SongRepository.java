package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface SongRepository extends JpaRepository<Song, Long> {

    Page<Song> findByTitleContaining(@RequestParam("title")String title, Pageable pageable);

    Page<Song> findByArtistContaining(@RequestParam("artist")String artist, Pageable pageable);

    Page<Song> findByAlbumContaining(@RequestParam("album")String album, Pageable pageable);

    Page<Song> findByGenreContaining(@RequestParam("genre")String genre, Pageable pageable);
}
