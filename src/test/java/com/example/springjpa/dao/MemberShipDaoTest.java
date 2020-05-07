package com.example.springjpa.dao;

import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.BasicMemberShip;
import com.example.springjpa.model.Membership;
import com.example.springjpa.model.Person;
import com.example.springjpa.model.PreminumMembership;
import com.example.springjpa.util.Util;
import org.junit.Assert;
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
@SpringBootTest(classes =  SpringJpaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class MemberShipDaoTest {

    @Autowired
    private MemberShipDao memberShipDao;

    @Autowired
    PersonRep personRep;
    @Test
    public void addBasicMembership(){
        BasicMemberShip basicMemberShip = new BasicMemberShip("12sada6500");
        basicMemberShip.setExpiryDate(Date.valueOf(LocalDate.now().plusMonths(1)));
        Assert.assertNotEquals(0,memberShipDao.addMembership(basicMemberShip));
    }

    @Test
    public  void addPermiumMembership(){
        PreminumMembership preminumMembership = new PreminumMembership("vo12asn111");
        preminumMembership.setExpiryDate(Date.valueOf(LocalDate.now().plusMonths(12)));
        Assert.assertNotEquals(0,memberShipDao.addMembership(preminumMembership));
    }

    @Test
    public void getAllMembership(){
        this.addBasicMembership();
        this.addPermiumMembership();
        Util.printRow(this.memberShipDao.getMemberList());
    }

    @Test
    public  void  getMemberShipe() throws NotFoundException {
        int personId = personRep.insertPerson(new Person("shubham","khandelwal",null));
        Membership membership = new BasicMemberShip("dhs27echw");
        ((BasicMemberShip) membership).setExpiryDate(Date.valueOf(LocalDate.now().plusMonths(1)));
        membership.setPerson(this.personRep.findById(personId));
        Assert.assertNotEquals(0,memberShipDao.addMembership(membership));
        Util.printRow(memberShipDao.getMemberList());

    }


}