package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "person") //if class name match with table name no need to annotate @Table
//@NamedQuery(name = "find_all_person",query = "select p from Person p")
//@NamedQuery(name = "find_person_by_name",query = "select p from Person p where firstName = :firstNane")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_person",query = "select p from Person p"),
        @NamedQuery(name = "find_person_by_name",query = "select p from Person p where firstName = :firstNane",lockMode = LockModeType.PESSIMISTIC_READ)
})
public class Person {

    public Person() {
    }

    public Person(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue //it will generate id automatically , so i dont need to generate id
    @Column(name = "id")
    private int personId;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Embedded
    private Address address;

    private Date birthDate; //name match with table column name so no need to annotate @Column

    @Column(name = "last_update",nullable = true)
    @UpdateTimestamp
    private Date lastUpdateDate;

    @Column(name = "created_date",nullable = true)
    @CreationTimestamp
    private Date createdDate;

    //From Issue card i can get the Person because i have foreign key so issue card is the owning side. But from Person
    //i cannot get the issuecard because i don't have the foreign key of that so one way to do the bi-directional is too
    //add the foreign key of issue_card and we can map back or if we dont want that in JPA we have mapped attribute in
    //@OneToOne where the value will be the varaible which is map from owning side, so in issuecard we have variable person
    //who has the link so we will put that.
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "person")
    private IssueCard issueCard;
}
