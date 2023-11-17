package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findBySongId(@RequestParam("song_id")Long songId, Pageable pageable);
    Review findByUserEmailAndSongId(String userEmail, Long songId);
}
