package com.example.springjpa.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@ToString(callSuper = true)
@Getter
@Entity
public class PreminumMembership extends Membership {
    public PreminumMembership(String membershipvoucher) {
        super(membershipvoucher);
    }

    public PreminumMembership(){}

    @Column(name = "issue_card_no",nullable = false)
    private int issueCardCount = 10;

    @Column(name = "expire_date",nullable = false)
    private Date expiryDate;

    @Column(name = "free_book_no",nullable = false)
    private int freeBook =  20;

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
