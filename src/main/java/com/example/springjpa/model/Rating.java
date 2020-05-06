package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "rating")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_rating",query = "select r from Rating r"),
        @NamedQuery(name = "find_all_rating_highest_star_order",query = "select r from Rating r order by ratingStar desc")
})
public class Rating {

    public Rating(int ratingStar, String description) {
        this.ratingStar = ratingStar;
        this.description = description;
    }

    @Id
    @GeneratedValue
    private int id;

    @Column( name = "rating_star",nullable = false)
    private int ratingStar;

    private String description;

    @ManyToOne
    private IssueCard issueCard;


}
