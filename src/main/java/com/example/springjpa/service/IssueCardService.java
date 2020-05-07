package com.example.springjpa.service;

import com.example.springjpa.dao.BookDao;
import com.example.springjpa.dao.IssueCardDao;
import com.example.springjpa.dao.MemberShipDao;
import com.example.springjpa.exception.*;
import com.example.springjpa.model.*;
import com.example.springjpa.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class IssueCardService {

    @Autowired
    private IssueCardDao issueCardDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private MemberShipDao memberShipDao;

    private Logger logger = LoggerFactory.getLogger(IssueCardService.class);

    public int getIssueCard(int personid) throws OutOFIssueCardExcpetion,MemberShipExpiration{
        Membership membership = memberShipDao.getMemberlistByPerson(personid);
        if(membership instanceof BasicMemberShip){
            if(Date.valueOf(LocalDate.now()).compareTo(((BasicMemberShip) membership).getExpiryDate()) >=0){
                throw  new MemberShipExpiration("Your membership has been expired. Please renew you membership to continue");
            }
            int issueCount = ((BasicMemberShip) (membership)).getIssueCardCount();
            if(issueCount == 0)
                throw new OutOFIssueCardExcpetion("Your Plan has reached max issue card. You cannot have new Issue card. Upgrade your plan to premium to have more issue card");
            issueCount--;
            ((BasicMemberShip) (membership)).setIssueCardCount(issueCount);
            memberShipDao.updateMembership(membership);
        }
        else if(membership instanceof PreminumMembership){
            if(Date.valueOf(LocalDate.now()).compareTo(((BasicMemberShip) membership).getExpiryDate())>=0){
                throw  new MemberShipExpiration("Your membership has been expired. Please renew you membership to continue");
            }
            int issueCount = ((PreminumMembership) (membership)).getIssueCardCount();
            if(issueCount == 0)
                throw new OutOFIssueCardExcpetion("Your Plan has reached max issue card. You cannot have new Issue card.");
            ((BasicMemberShip) (membership)).setIssueCardCount(issueCount--);
            memberShipDao.updateMembership(membership);
        }
        else {
            throw new OutOFIssueCardExcpetion("Upgrade your plan to basic or preminum membership");
        }
        return this.getNewIssueCard();
    }

    public int getNewIssueCard(){
        byte[] array = new byte[20]; // length is bounded by 7
        new Random().nextBytes(array);
        IssueCard issueCard = new IssueCard(new String(array, Charset.forName("UTF-8")), Date.valueOf(LocalDate.now().plusMonths(36)));
        return issueCardDao.insetIssueCard(issueCard) ;
    }

    public boolean issueNewBook(int bookId, String issueCardId, Person person) throws AlreadyIssuedBook, NotFoundException,IssueCardExpiration {
        List<IssueCard> issueCards = issueCardDao.getIssueCardWithBookId(bookId);
        if(issueCards.stream().count() >0){
            logger.error("Book is already issued");
            throw new AlreadyIssuedBook("Book is already issued to some user. Issue id of the user ".concat(Optional.of(issueCards.get(0)).isPresent() ? issueCards.get(0).toString(): "Unidentified"));
        }
        else{
            Book book = bookDao.findById(bookId);
            IssueCard issueCard = issueCardDao.getIssueCardWithIssueId(issueCardId);
            if(issueCardExpired(issueCard)){
                logger.error("Book cannot be issued . You card is expired. Please renew your card");
                throw new IssueCardExpiration("Book cannot be issued . You card is expired. Please renew your card");
            }
            issueCard.setPerson(person);
            book.setIssueCard(issueCard);
            issueCard.setBook(book);

            issueCardDao.updateIssueCard(issueCard);
            logger.info("Booked Issue successfully");
            return true;
        }

    }

    public boolean issueCardExpired(IssueCard issueCard){
        return  Date.valueOf(LocalDate.now()).compareTo(issueCard.getExpirationDate())>0?true:false;
    }

    public IssueCard reIssueCard(Date date,IssueCard issueCard) throws NotFoundException{
        issueCard.setExpirationDate(date);
        issueCardDao.updateIssueCard(issueCard);
        return issueCardDao.findById(issueCard.getId());
    }

}
