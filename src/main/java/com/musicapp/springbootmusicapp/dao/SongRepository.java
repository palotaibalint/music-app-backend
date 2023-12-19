package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> findByTitleIgnoreCaseContaining(String title, Pageable pageable);

    Page<Song> findByArtistIgnoreCaseContaining(String artist, Pageable pageable);

    Page<Song> findByAlbumIgnoreCaseContaining(String album, Pageable pageable);

    List<Song> findTop5ByOrderByClicksDesc();

    Page<Song> findAll(Pageable pageable);

    @Query(value = "SELECT s.* FROM Song s WHERE s.song_id IN " +
            "(SELECT ps.song_id FROM playlist_song ps " +
            "GROUP BY ps.song_id ORDER BY COUNT(ps.playlist_id) DESC LIMIT 5)",
            nativeQuery = true)
    List<Song> findTopSongsInPlaylists();


}

