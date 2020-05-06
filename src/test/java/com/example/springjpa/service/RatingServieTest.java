package com.example.springjpa.service;

import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.dao.BookDao;
import com.example.springjpa.dao.IssueCardDao;
import com.example.springjpa.dao.PersonRep;
import com.example.springjpa.dao.RatingDao;
import com.example.springjpa.exception.AlreadyIssuedBook;
import com.example.springjpa.exception.IssueCardExpiration;
import com.example.springjpa.exception.ItemNotAssocaited;
import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Book;
import com.example.springjpa.model.IssueCard;
import com.example.springjpa.model.Person;
import com.example.springjpa.model.Rating;
import com.example.springjpa.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJpaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class RatingServieTest {
    @Autowired
    private IssueCardService issueCardService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private IssueCardDao issueCardDao;
    @Autowired
    private PersonRep personRep;

    private IssueCard issueCard;

    private Person person;

    private int bookId =0 ;

    private int personId =0 ;

    @Autowired
    private RatingServie ratingServie;

    @Autowired
    private RatingDao ratingDao;

    @Before
    public void setup(){
        Book book = new Book("Learn Java","Author1","Programming",1);
        Person person = new Person("Shubham","Khandelwal",new Date(1l));
        this.personId = personRep.insertPerson(person);
        this.bookId = bookDao.insetBook(book);
        int issueId = issueCardService.getNewIssueCard();
        Util.printRow(issueCardDao.getAllIssueCards());
        try {
            this.issueCard= issueCardDao.findById(issueId);
            this.person = personRep.findById(this.personId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void submitRating() throws ItemNotAssocaited, AlreadyIssuedBook , NotFoundException, IssueCardExpiration {
        issueCardService.issueNewBook(this.bookId,issueCard.getIssueNumber(),this.person);
        Rating rating = new Rating(4,"I like the book");
        Assert.assertNotEquals("Issue Card submit a rating ",ratingServie.submitRating(rating,this.issueCard),0);

        rating = new Rating(5,"I like the book");
        Assert.assertNotEquals("Issue Card submit a rating ",ratingServie.submitRating(rating,this.issueCard),0);

        rating = new Rating(2,"I like the book");
        Assert.assertNotEquals("Issue Card submit a rating ",ratingServie.submitRating(rating,this.issueCard),0);

        Util.printRow(issueCardDao.getIssueCardwith2MaxRating());

        Assert.assertEquals("Bi-directional check without affecting table by mapped attribute",3,issueCardDao.findById(issueCard.getId()).getRatingList().size());

        Assert.assertEquals("Check to have Issue Card which have more then 2 rating by native query",1,issueCardDao.getIssueCardwith2MaxRating().size());

        Assert.assertEquals("Check Whether rating run by native query with rating star order desc",5,ratingDao.getAllRatingsByStarOrder().get(0).getRatingStar());

        Assert.assertNotEquals("Test For inner join",0,issueCardDao.getIssueCardJoinBook());

        Assert.assertNotEquals("Test For left join",0,issueCardDao.getIssueCardLeftJoinBook());

        Assert.assertNotEquals("Test For cross join",0,issueCardDao.getIssueCardCrossJoinBook());
    }

    @Test(expected = ItemNotAssocaited.class)
    public void submitRatingExpectException() throws ItemNotAssocaited, AlreadyIssuedBook , NotFoundException, IssueCardExpiration {
        Rating rating = new Rating(4,"I like the book");
        Assert.assertNotEquals("Failed to submit a rating when no book is issued",ratingServie.submitRating(rating,this.issueCard),0);
        ratingServie.printRating();
    }
}