package com.example.springjpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "membership")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
@DiscriminatorColumn(name = "membership_type")
@NamedQueries({
        @NamedQuery(name = "get_all_membership",query = "select m from Membership m"),
        @NamedQuery(name = "get_all_membership_by_person",query = "select m from Membership m where m.person.personId = :personId")
})
public abstract class Membership  {

    public Membership(String membershipvoucher) {
        this.membershipvoucher = membershipvoucher;
    }

    public Membership(){}

    @GeneratedValue
    @Id
    @Column(name = "id",nullable = false)
    public int memberId;

    @Column(name = "voucher",nullable = false)
    public String membershipvoucher;

    @OneToOne
    public Person person;

    @Column(name = "membership_type" ,insertable = false,updatable = false)
    private String membershipType;


}
