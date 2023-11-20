package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.entity.Review;
import com.musicapp.springbootmusicapp.requestmodels.ReviewRequest;
import com.musicapp.springbootmusicapp.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/user")
    public ResponseEntity<Review> getReviewByUserEmailAndSongId(@RequestParam String userName, @RequestParam Long songId) {
        Review review = reviewService.getReviewByUserEmailAndSongId(userName,songId);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @GetMapping("/song")
    public ResponseEntity<Page<Review>> getReviewsBySongId(@RequestParam Long songId, Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewsBySongId(songId,pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Review> postReviewByUsername(@RequestBody String userName,@RequestBody ReviewRequest reviewRequest) throws Exception {
        Review review = reviewService.postReview(userName, reviewRequest);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

}
