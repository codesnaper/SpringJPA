package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Person;
import com.example.springjpa.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional
@Repository("personRep")
public class PersonRep implements IPersonRep {

    private Logger logger = LoggerFactory.getLogger(PersonRep.class);
    @PersistenceContext
    EntityManager entityManager;

    public Person findById(int id) throws NotFoundException{
        Person person =entityManager.find(Person.class,id);
        if(person!=null){
            return person;
        }
        throw  new NotFoundException("Data not found for id "+id);
    }

    public Person updatePerson(Person person){
         Person person1 =  entityManager.merge(person);
        logger.info("Upadted Data {} has been inserted to DB", person1.toString());
        return person1;
    }

    public int  insertPerson(Person person){
        entityManager.persist(person);
        entityManager.flush();
        logger.info("Data {} has been inserted to DB", person.toString());
        return person.getPersonId();
    }

    public void deletePerson(int id) throws NotFoundException{
        Person person = this.findById(id);
        entityManager.remove(person);
        logger.info("Data {} has been deleted from DB", person.toString());
    }

    public List<Person> getAllPerson(){
        TypedQuery<Person> personTypedQuery = entityManager.createNamedQuery("find_all_person",Person.class);
        return  personTypedQuery.getResultList();
    }

    public List<Person> getPersonFilterName(String name){
        TypedQuery<Person> personTypedQuery = entityManager.createNamedQuery("find_person_by_name",Person.class).setParameter("firstNane","Shubham");
        logger.info("Search Result >>");
        this.printRow(personTypedQuery.getResultList());
        return  personTypedQuery.getResultList();
    }

    public List<Person> getPersonFilterLastNameWithEntityManageCreateQuery(String lastName){
        TypedQuery<Person> personTypedQuery = entityManager.createQuery("select p from Person p where lastName = :lastName",Person.class).setParameter("lastName",lastName);
        logger.info("Search Result >>");
        this.printRow(personTypedQuery.getResultList());
        return  personTypedQuery.getResultList();
    }

    public void entityManagerTransaction(){
        Person person = new Person("Khandelwal","shubham",new Date(2l));
        entityManager.persist(person); //It will not send the data to database until the method finish due to transaction adn track for change
        person.setLastName("LastName"); //As it is in one transaction so above operation will keep the track of change of object and if change it will automatically push
        this.printRow(this.getAllPerson());
    }

    public  int entityManagerflush(){
        Person person = new Person("Khandelwal","shubham",new Date(2l));
        entityManager.persist(person);
        entityManager.flush(); //Flush will send the data to database at this point but still it will track the change for transaction
        this.printRow(this.getAllPerson());

        person.setFirstName("Shiva");
        entityManager.flush();
        this.printRow(this.getAllPerson());

        person.setLastName("Kumar");
        entityManager.flush();
        this.printRow(this.getAllPerson());

        return person.getPersonId();
    }

    public void entityManagerDetach(){
        Person person = new Person("Shubham","Khandelwal",new Date(2l));
        Person person1 = new Person("Shiva","Kumar",new Date(2l));

        entityManager.persist(person);
        entityManager.persist(person1);
        this.printRow(this.getAllPerson());

        person.setFirstName("S");
        this.printRow(this.getAllPerson());

        entityManager.detach(person1);// At this point whatever the change happen to object will not been track off
        person1.setFirstName("S");
        this.printRow(this.getAllPerson());

    }

    public void entityManagerClear(){
        Person person = new Person("Shubham","Khandelwal",new Date(2l));
        Person person1 = new Person("Shiva","Kumar",new Date(2l));

        entityManager.persist(person);
        entityManager.persist(person1);
        this.printRow(this.getAllPerson());

        entityManager.clear(); //Not to track anything
        person.setFirstName("S");
        this.printRow(this.getAllPerson());


        person1.setFirstName("S");
        this.printRow(this.getAllPerson());

    }

    public void entityManagerRefresh(){
        Person person = new Person("Shubham","Khandelwal",new Date(2l));
        Person person1 = new Person("Shiva","Kumar",new Date(2l));

        entityManager.persist(person);
        entityManager.persist(person1);
        entityManager.flush();
        this.printRow(this.getAllPerson());

        person.setFirstName("S");
        person1.setFirstName("S");
        entityManager.refresh(person1);
        entityManager.refresh(person); //I dont want the change of person
        this.printRow(this.getAllPerson());
    }

    public List<Person> entityManagerNativeQueryFilterFirstName(String firstName){
        Query personTypedQuery = entityManager.createNativeQuery("select * from person where first_name = :firstName",Person.class);
        personTypedQuery.setParameter("firstName",firstName);
        List<Person> personList =  personTypedQuery.getResultList();
        logger.info("Search Result");
        this.printRow(personList);
        return personList;
    }

    public int updateRowNativeQuery(){
        Query perQuery = entityManager.createQuery("UPDATE person SET created_date = null",Person.class);
        return perQuery.executeUpdate();
    }

    public void printRow(List<Person> persons){
        Util.printRow(persons);
    }





 }
