package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"ratingStarDB"})
@Entity
@Table(name = "rating")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_rating",query = "select r from Rating r"),
        @NamedQuery(name = "find_all_rating_highest_star_order",query = "select r from Rating r order by ratingStar desc")
})
public class Rating {

    public Rating(RatingStar ratingStar, String description) {
        this.ratingStar = ratingStar;
        this.description = description;
    }

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(value = EnumType.STRING)
    private RatingStar ratingStar;

    @Column( name = "rating_star",nullable = false)
    private int ratingStarDB;

    private String description;

    @ManyToOne
    private IssueCard issueCard;

    @PrePersist
    @PreUpdate
    public void updateRatingStar(){
        this.ratingStarDB = this.ratingStar.getValue();
    }

    @PostLoad
    public void fillRatingStar(){
        this.ratingStar = RatingStar.getRatingStar(this.ratingStarDB);
    }





}
