package com.example.springjpa.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@ToString(callSuper = true)
@Getter
@Entity
public class BasicMemberShip extends Membership{
    public BasicMemberShip(String membershipvoucher) {
        super(membershipvoucher);
    }

    public BasicMemberShip(){}

    @Column(name = "issue_card_no",nullable = false)
    private int issueCardCount = 2;

    @Column(name = "expire_date",nullable = false)
    private Date expiryDate;

    @Column(name = "free_book_no",nullable = false)
    private int freeBook =  6;

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setIssueCardCount(int issueCardCount) {
        this.issueCardCount = issueCardCount;
    }

    public void setFreeBook(int freeBook) {
        this.freeBook = freeBook;
    }
}
