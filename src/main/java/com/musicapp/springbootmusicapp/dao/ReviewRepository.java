package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findBySongId(@RequestParam("song_id")Long songId, Pageable pageable);
    Review findByUserEmailAndSongId(String userEmail, Long songId);

    Optional<Page<Review>> findByUserNameOrderByDateDesc(String username, Pageable pageable);

    @Query("SELECT r.songId, COUNT(r) FROM Review r GROUP BY r.songId ORDER BY COUNT(r) DESC")
    List<Object[]> countReviewsPerSong();
}
