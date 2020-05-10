package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"ratingList"})
@Entity
@Table(name = "issue_card")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_issueCard",query = "select ic from IssueCard ic"),
        @NamedQuery(name = "getIssueCardWithBook",query = "select ic from IssueCard ic where book_id=:bookId"),
        @NamedQuery(name = "find_issue_id",query = "select ic from IssueCard ic where issueNumber=:issueNumber"),
        @NamedQuery(name = "issuecard_with_more_2_ratings", query = "select ic from IssueCard ic where size(ic.ratingList) > 2"),
        @NamedQuery(name = "issuecard_join_book" , query = "select ic, b from IssueCard ic JOIN ic.book b"),
        @NamedQuery(name = "issuecard_leftJoin_book", query = "select ic, b from IssueCard ic JOIN ic.book b"),
        @NamedQuery(name = "issuecard_crossJoin_book", query = "select ic, b from IssueCard ic, Book b"),
        @NamedQuery(name = "find_all_issueCard_joinFetch_person",query = "select ic from IssueCard JOIN FETCH ic.person")
})
public class IssueCard {

    public IssueCard(){}
    public IssueCard(String issueNumber, Date expirationDate) {
        this.issueNumber = issueNumber;
        this.expirationDate = expirationDate;
    }

    @Id
    @GeneratedValue
    @Column( name = "id",nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personId")
    private Person person;

    @Column(name = "issue_no",nullable = false)
    private String issueNumber;

    @CreationTimestamp
    @Column(name = "created_date",nullable = false)
    private Date creationDate;

    @Column(name = "expired_date",nullable = false)
    private Date expirationDate;

    @OneToOne
    private Book book;

    @OneToMany(mappedBy = "issueCard")
    private List<Rating> ratingList  = new ArrayList<>();
}
