package com.musicapp.springbootmusicapp.requestmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;

    private Long songId;

    private String userName;

    private Optional<String> reviewTitle;

    private Optional<String> reviewDescription;


}
