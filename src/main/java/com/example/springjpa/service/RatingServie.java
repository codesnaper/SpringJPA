package com.example.springjpa.service;

import com.example.springjpa.dao.RatingDao;
import com.example.springjpa.exception.ItemNotAssocaited;
import com.example.springjpa.model.IssueCard;
import com.example.springjpa.model.Rating;
import com.example.springjpa.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingServie {

    @Autowired
    private RatingDao ratingDao;
    public int submitRating(Rating rating, IssueCard issueCard) throws ItemNotAssocaited{
        List<Rating> ratings = issueCard.getRatingList();
        ratings.add(rating);
        issueCard.setRatingList(ratings);
        rating.setIssueCard(issueCard);
        if(issueCard.getBook() ==null){
            throw  new ItemNotAssocaited("We valued your feedback. We would lie to take the book and then provide the feedback");
        }
        return ratingDao.insetRating(rating);
    }

    public void printRating(){
        Util.printRow(ratingDao.getAllRatings());
    }


}
