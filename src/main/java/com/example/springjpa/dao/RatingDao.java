package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.IssueCard;
import com.example.springjpa.model.Rating;
import com.example.springjpa.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RatingDao {
    private Logger logger = LoggerFactory.getLogger(RatingDao.class);
    @PersistenceContext
    EntityManager entityManager;

    public Rating findById(int id) throws NotFoundException {
        Rating rating =entityManager.find(Rating.class,id);
        if(rating!=null){
            return rating;
        }
        throw  new NotFoundException("Data not found for id "+id);
    }

    public Rating updateRating(Rating rating){
        Rating rating1 =  entityManager.merge(rating);
        logger.info("Upadted Data {} has been inserted to DB", rating1.toString());
        return rating1;
    }

    public int  insetRating(Rating rating){
        entityManager.persist(rating);
        entityManager.flush();
        logger.info("Data {} has been inserted to DB", rating.toString());
        return rating.getId();
    }

    public void deleteRating(int id) throws NotFoundException{
        Rating rating = this.findById(id);
        entityManager.remove(rating);
        logger.info("Data {} has been deleted from DB", rating.toString());
    }

    public List<Rating> getAllRatings(){
        TypedQuery<Rating> ratingTypedQuery = entityManager.createNamedQuery("find_all_rating",Rating.class);
        List<Rating> ratings = ratingTypedQuery.getResultList();
        Util.printRow(ratings);
        return ratings;
    }

    public List<Rating> getAllRatingsByStarOrder(){
        TypedQuery<Rating> ratingTypedQuery = entityManager.createNamedQuery("find_all_rating_highest_star_order",Rating.class);
        List<Rating> ratings = ratingTypedQuery.getResultList();
        Util.printRow(ratings);
        return ratings;
    }
}
