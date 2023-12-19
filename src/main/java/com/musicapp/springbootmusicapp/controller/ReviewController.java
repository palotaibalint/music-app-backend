package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.entity.Review;
import com.musicapp.springbootmusicapp.requestmodels.ReviewRequest;
import com.musicapp.springbootmusicapp.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/private/user/song")
    public ResponseEntity<Review> getReviewByUserEmailAndSongId(@RequestParam String userEmail, @RequestParam Long songId) {
        Review review = reviewService.getReviewByUserEmailAndSongId(userEmail,songId);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @GetMapping("/public/by-id")
    public ResponseEntity<Page<Review>> getReviewsBySongId(@RequestParam Long songId, Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewsBySongId(songId,pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/private/post")
    public ResponseEntity<Review> postReviewByUsername(@RequestParam String userName,@RequestParam String userEmail,@RequestBody ReviewRequest reviewRequest) throws Exception {
        Review review = reviewService.postReview( userName, userEmail, reviewRequest);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @GetMapping("/private/user/latest")
    public ResponseEntity<Page<Review>> getLatestReviewsByUser(@RequestParam String username) {
        Optional<Page<Review>> reviews = reviewService.getLatestReviewsByUserName(username);
        if(reviews.isPresent()){
            return ResponseEntity.ok(reviews.get());
        }
        else{
            Page<Review> emptyPage = Page.empty();
            return ResponseEntity.ok(emptyPage);
        }

    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<Void> deleteReview(@RequestParam String userEmail, @RequestParam Long songId) {
        try {
            reviewService.deleteReview(userEmail, songId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewService.ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
