package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
