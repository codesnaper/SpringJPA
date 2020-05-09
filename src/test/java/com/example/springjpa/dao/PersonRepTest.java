package com.example.springjpa.dao;

import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Address;
import com.example.springjpa.model.Person;
import com.example.springjpa.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJpaApplication.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PersonRepTest {

    @Autowired
    PersonRep personRep;

    Person person;

    private int personId;
    @Before
    public void addData(){
        this.person = new Person("Shubham","Khandelwal",new Date(1l));
        this.personId = personRep.insertPerson(person);
    }

    @Test
    public void addDataWithAddress(){
        this.person = new Person("Shubham","Khandelwal",new Date(1l));
        Address address = new Address("line1","line2","Hyderabad","India","500012");
        this.person.setAddress(address);
        this.personId = personRep.insertPerson(person);
        Util.printRow(personRep.getAllPerson());
    }
    @Test
    public void findById() throws NotFoundException {
        Person person = personRep.findById(this.personId);
        Assert.assertEquals(this.person.getFirstName(),person.getFirstName());
    }

    @org.junit.Test(expected = NotFoundException.class)
    public void findByIdWithException() throws NotFoundException {
        personRep.findById(2);

    }

    @Test
    public void updatePerson() {
        person.setLastName("Kumar");
        Person person1 = personRep.updatePerson(person);
        Assert.assertEquals("Kumar",person1.getLastName());
    }

    @Test
    public void insertPerson() {
        Person person2 = new Person("Shiva","Kumar",new Date(3l));
        personRep.insertPerson(person2);
        Assert.assertTrue(true);
    }

    //@DirtiesContext Will rollback the transaction done at method level
    @Test
    public void deletePerson() throws NotFoundException {
        personRep.deletePerson(this.personId);
        personRep.printRow(personRep.getAllPerson());
        Assert.assertTrue(true);
    }

    @Test
    public void getAllPerson() {
        personRep.printRow(personRep.getAllPerson());
        Assert.assertNotNull(personRep.getAllPerson());
    }

    @Test
    public void entityManagerTransaction() throws NotFoundException {
        personRep.entityManagerTransaction();
        Assert.assertEquals("LastName",personRep.findById(2).getLastName());
    }

    @Test
    public void entityManagerflush() throws NotFoundException {
        int id = personRep.entityManagerflush();
        Assert.assertEquals("Kumar",personRep.findById(id).getLastName());
    }

    @Test
    public void entityManagerDetach() throws NotFoundException {
        personRep.entityManagerDetach();
        Assert.assertNotEquals("S",personRep.findById(3).getFirstName());
        Assert.assertEquals("S",personRep.findById(2).getFirstName());
    }

    @Test
    public void entityManagerClear() throws NotFoundException {
        personRep.entityManagerClear();
        Assert.assertNotEquals("S",personRep.findById(3).getFirstName());
        Assert.assertNotEquals("S",personRep.findById(2).getFirstName());
    }

    @Test
    public void entityManagerRefresh() throws NotFoundException{
        personRep.entityManagerRefresh();
        Assert.assertNotEquals("S",personRep.findById(3).getFirstName());
        Assert.assertNotEquals("S",personRep.findById(2).getFirstName());
    }

    @Test
    public void getPersonFilterName() {
        List<Person> personList = personRep.getPersonFilterName("Shubham");
        Assert.assertNotEquals(personList.size(),0);
    }

    @Test
    public void getPersonFilterLastNameWithEntityManageCreateQuery() {
        List<Person> personList = personRep.getPersonFilterLastNameWithEntityManageCreateQuery("Khandelwal");
        Assert.assertNotEquals(personList.size(),0);
    }

    @Test
    public void entityManagerNativeQueryFilterFirstName(){
        Assert.assertNotEquals(personRep.entityManagerNativeQueryFilterFirstName("Shubham"),0);
    }

    @Test
    public void updateRowNativeQuery(){
        Assert.assertNotEquals(personRep.updateRowNativeQuery(),0);
    }
}