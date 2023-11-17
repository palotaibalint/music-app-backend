package com.musicapp.springbootmusicapp.controller;

import com.musicapp.springbootmusicapp.entity.Review;
import com.musicapp.springbootmusicapp.requestmodels.ReviewRequest;
import com.musicapp.springbootmusicapp.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Review getReviewByUserEmailAndSongId(@RequestParam String userName,@RequestParam Long songId) {
        return reviewService.getReviewByUserEmailAndSongId(userName,songId);
    }

    @GetMapping("/song")
    public Page<Review> getReviewsBySongId(@RequestParam Long songId, @RequestParam Pageable pageable) {
        return reviewService.getReviewsBySongId(songId,pageable);
    }

    @PostMapping("/user")
    public void postReviewByUsername(@RequestBody String userName,@RequestBody ReviewRequest reviewRequest) throws Exception {
        reviewService.postReview(userName, reviewRequest);
    }

}
