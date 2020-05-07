package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Membership;
import com.example.springjpa.model.Person;
import com.example.springjpa.util.Util;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MemberShipDao {

    @PersistenceContext
    EntityManager entityManager;

    public Membership getMembership(int id) throws NotFoundException{
        Membership membership = entityManager.find(Membership.class,id);
        if(membership == null)
            throw new NotFoundException("Membership is not there");
        return membership;
    }

    public int addMembership(Membership membership){
        entityManager.persist(membership);
        entityManager.flush();
        return membership.getMemberId();
    }

    public int updateMembership(Membership membership){
        System.out.println("Upda"+membership.toString());
        entityManager.merge(membership);
        entityManager.flush();
        return membership.getMemberId();
    }

    public List<Membership> getMemberList(){
        TypedQuery<Membership> membershipTypedQuery = entityManager.createNamedQuery("get_all_membership",Membership.class);
        return membershipTypedQuery.getResultList();
    }

    public Membership getMemberlistByPerson(int personId){
        TypedQuery<Membership> membershipTypedQuery = entityManager.createNamedQuery("get_all_membership_by_person",Membership.class);
        membershipTypedQuery.setParameter("personId",personId);
        return  membershipTypedQuery.getSingleResult();
    }


}
