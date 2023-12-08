package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Comment;
import com.musicapp.springbootmusicapp.service.CommentService;
import com.musicapp.springbootmusicapp.service.ReviewService;
import com.musicapp.springbootmusicapp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/public/review-comments")
    public ResponseEntity<Optional<List<Comment>>> getCommentsByReviewId(@RequestParam(name="id",defaultValue="") Long reviewId ){
        return new ResponseEntity<>(commentService.findByReviewId(reviewId), HttpStatus.OK);
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<Void> deleteComment(@RequestParam Long id) {
        try {
            commentService.deleteCommentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewService.ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/private/addComment")
    public ResponseEntity<Comment> addComment(
            @RequestParam("username") String userName,
            @RequestParam("text") String text,
            @RequestParam("review-id") Long reviewId
            )throws IOException{
        Comment newComment=new Comment(userName,text,reviewId);
        commentService.addComment(newComment);
        return new ResponseEntity<>(newComment,HttpStatus.CREATED);
    }
}
