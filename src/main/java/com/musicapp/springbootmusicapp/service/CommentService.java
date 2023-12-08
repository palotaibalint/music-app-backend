package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.CommentRepository;
import com.musicapp.springbootmusicapp.entity.Comment;
import com.musicapp.springbootmusicapp.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Optional<List<Comment>> findByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }

    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }
}
