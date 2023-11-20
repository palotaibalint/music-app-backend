package com.musicapp.springbootmusicapp.service;

import com.musicapp.springbootmusicapp.dao.ReviewRepository;
import com.musicapp.springbootmusicapp.dao.SongRepository;
import com.musicapp.springbootmusicapp.entity.Review;
import com.musicapp.springbootmusicapp.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }

    public Review postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
        Review validateReview = reviewRepository.findByUserEmailAndSongId(userEmail, reviewRequest.getSongId());
        if(validateReview != null){
            throw new Exception("Review already created");
        }
        Review review = new Review();
        review.setSongId(reviewRequest.getSongId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        review.setUserName(reviewRequest.getUserName());
        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        if(reviewRequest.getReviewTitle().isPresent()){
            review.setReviewTitle(reviewRequest.getReviewTitle().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
       return  reviewRepository.save(review);
    }

    public boolean userReviewListed(String userEmail, Long songId){
        Review validateReview = reviewRepository.findByUserEmailAndSongId(userEmail,songId);
        if(validateReview!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public Page<Review> getReviewsBySongId(Long songId, Pageable pageable) {
        return reviewRepository.findBySongId(songId,pageable);
    }

    public Review getReviewByUserEmailAndSongId(String userEmail, Long songId) {
        return reviewRepository.findByUserEmailAndSongId(userEmail,songId);
    }
}
