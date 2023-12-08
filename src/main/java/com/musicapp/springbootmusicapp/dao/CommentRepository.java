package com.musicapp.springbootmusicapp.dao;

import com.musicapp.springbootmusicapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findByReviewId(Long reviewId);
    void deleteById(Long id);

}
