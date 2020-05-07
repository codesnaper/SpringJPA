package com.example.springjpa.service;

import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.dao.BookDao;
import com.example.springjpa.dao.IssueCardDao;
import com.example.springjpa.dao.MemberShipDao;
import com.example.springjpa.dao.PersonRep;
import com.example.springjpa.exception.*;
import com.example.springjpa.model.*;
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
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJpaApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IssueCardServiceTest {

    @Autowired
    private IssueCardService issueCardService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private IssueCardDao issueCardDao;
    @Autowired
    private PersonRep personRep;
    @Autowired
    private MemberShipDao memberShipDao;

    private IssueCard issueCard;

    private Person person;

    private int bookId =0 ;

    private int personId =0 ;

    @Before
    public void setUp(){
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
    public void issueBook() throws AlreadyIssuedBook, NotFoundException,IssueCardExpiration {
        Assert.assertTrue(issueCardService.issueNewBook(this.bookId,issueCard.getIssueNumber(),this.person));
        Util.printRow(issueCardDao.getAllIssueCards());
        Assert.assertNotNull(bookDao.findById(this.bookId).getIssueCard());

    }

    @Test(expected = AlreadyIssuedBook.class)
    public void issueBookException() throws AlreadyIssuedBook, NotFoundException, IssueCardExpiration {
        issueCardService.issueNewBook(this.bookId,issueCard.getIssueNumber(),this.person);
        issueCardService.issueNewBook(this.bookId,issueCard.getIssueNumber(),this.person);
        Util.printRow(issueCardDao.getAllIssueCards());
    }

    @Test(expected = IssueCardExpiration.class)
    public void issueCardException() throws NotFoundException,IssueCardExpiration,AlreadyIssuedBook{
        Date expirationDate = Date.valueOf(LocalDate.now().minusDays(20));
        IssueCard issueCard = issueCardService.reIssueCard(expirationDate,this.issueCard);
        issueCardService.issueNewBook(this.bookId,issueCard.getIssueNumber(),this.person);
    }

    @Test
    public void getIssueCardInfo()throws  NotFoundException{
        Util.printRow(issueCardDao.getAllIssueCards());
        Assert.assertTrue(true);
    }

    @Test
    public void getNonIssuedBook() throws AlreadyIssuedBook, NotFoundException,IssueCardExpiration{
        Book book = new Book("Learn Python","Author1","Programming",1);
        Book book1 = new Book("Learn Golang","Author1","Programming",1);
        int bookId = bookDao.insetBook(book);
        int bookId1 = bookDao.insetBook(book1);
        issueCardService.issueNewBook(bookId,issueCard.getIssueNumber(),this.person);
        System.out.println(bookDao.findById(bookId1).getIssueCard());
        System.out.println(bookDao.findById(bookId).getIssueCard().toString());
        Assert.assertEquals(1,bookDao.getAllNonIssuedBook().size());
    }

    @Test
    public void getIssueCardAccMembership() throws NotFoundException, MemberShipExpiration, OutOFIssueCardExcpetion {
        int personId = personRep.insertPerson(new Person("Shubham","Khandelwal",null));
        BasicMemberShip membership = new BasicMemberShip("dhsj74ebdc");
        membership.setIssueCardCount(2);
        membership.setExpiryDate(Date.valueOf(LocalDate.now().plusMonths(1)));
        membership.setPerson(personRep.findById(personId));
        memberShipDao.addMembership(membership);
        Assert.assertNotEquals(0,issueCardService.getIssueCard(personId));
        Assert.assertNotEquals(0,issueCardService.getIssueCard(personId));
        Util.printRow(memberShipDao.getMemberList());
    }

    @Test(expected =  OutOFIssueCardExcpetion.class)
    public void getIssueCardAccMembershipExceede() throws NotFoundException, MemberShipExpiration, OutOFIssueCardExcpetion {
        int personId = personRep.insertPerson(new Person("Shubham","Khandelwal",null));
        BasicMemberShip membership = new BasicMemberShip("dhsj74ebdc");
        membership.setIssueCardCount(2);
        membership.setExpiryDate(Date.valueOf(LocalDate.now().plusMonths(1)));
        membership.setPerson(personRep.findById(personId));
        memberShipDao.addMembership(membership);
        Assert.assertNotEquals(0,issueCardService.getIssueCard(personId));
        Assert.assertNotEquals(0,issueCardService.getIssueCard(personId));
        issueCardService.getIssueCard(personId);
    }

    @Test(expected =  MemberShipExpiration.class)
    public void getIssueCardAccMembershipExpire() throws NotFoundException, MemberShipExpiration, OutOFIssueCardExcpetion {
        int personId = personRep.insertPerson(new Person("Shubham","Khandelwal",null));
        BasicMemberShip membership = new BasicMemberShip("dhsj74ebdc");
        membership.setIssueCardCount(2);
        membership.setExpiryDate(Date.valueOf(LocalDate.now().minusDays(30)));
        membership.setPerson(personRep.findById(personId));
        memberShipDao.addMembership(membership);

        issueCardService.getIssueCard(personId);
    }

}