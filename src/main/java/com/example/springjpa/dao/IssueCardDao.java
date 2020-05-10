package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Book;
import com.example.springjpa.model.IssueCard;
import com.example.springjpa.model.Person;
import com.example.springjpa.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Repository
public class IssueCardDao {

    private Logger logger = LoggerFactory.getLogger(IssueCardDao.class);
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    public IssueCard findById(int id) throws NotFoundException {
        IssueCard issueCard =entityManager.find(IssueCard.class,id);
        if(issueCard!=null){
            return issueCard;
        }
        throw  new NotFoundException("Data not found for id "+id);
    }

    public IssueCard updateIssueCard(IssueCard issueCard){
        IssueCard issueCard1 =  entityManager.merge(issueCard);
        logger.info("Upadted Data {} has been inserted to DB", issueCard1.toString());
        return issueCard1;
    }

    public int  insetIssueCard(IssueCard issueCard){
        entityManager.persist(issueCard);
        entityManager.flush();
        logger.info("Data {} has been inserted to DB", issueCard.toString());
        return issueCard.getId();
    }

    public void deleteIssueCard(int id) throws NotFoundException{
        IssueCard issueCard = this.findById(id);
        entityManager.remove(issueCard);
        logger.info("Data {} has been deleted from DB", issueCard.toString());
    }

    public List<IssueCard> getAllIssueCards(){
        TypedQuery<IssueCard> issueCardTypedQuery = entityManager.createNamedQuery("find_all_issueCard",IssueCard.class);
        List<IssueCard> issueCards = issueCardTypedQuery.getResultList();
        Util.printRow(issueCards);
        return issueCards;
    }

    public List<IssueCard> getIssueCardWithBookId(int bookId){
        TypedQuery<IssueCard> issueCardTypedQuery = entityManager.createNamedQuery("getIssueCardWithBook",IssueCard.class);
        issueCardTypedQuery.setParameter("bookId",bookId);
        List<IssueCard> issueCards = issueCardTypedQuery.getResultList();
        Util.printRow(issueCards);
        return issueCards;
    }

    public IssueCard getIssueCardWithIssueId(String issueNumber){
        TypedQuery<IssueCard> issueCardTypedQuery = entityManager.createNamedQuery("find_issue_id",IssueCard.class);
        issueCardTypedQuery.setParameter("issueNumber",issueNumber);
        IssueCard issueCards = issueCardTypedQuery.getSingleResult();
        return issueCards;
    }

    public List<IssueCard> getIssueCardwith2MaxRating(){
        TypedQuery<IssueCard> issueCardTypedQuery = entityManager.createNamedQuery("issuecard_with_more_2_ratings",IssueCard.class);
        List<IssueCard> issueCards = issueCardTypedQuery.getResultList();
        return issueCards;
    }

    public int getIssueCardJoinBook(){
        TypedQuery<Object[]> issueCardTypedQuery = entityManager.createNamedQuery("issuecard_join_book",Object[].class);
        List<Object[]> issueCards = issueCardTypedQuery.getResultList();
        issueCards.stream().forEach(objects -> Arrays.asList(issueCards.get(0)).stream().forEach(o -> System.out.println(o.toString())));
        return issueCards.size();
    }

    public int getIssueCardLeftJoinBook(){
        TypedQuery<Object[]> issueCardTypedQuery = entityManager.createNamedQuery("issuecard_leftJoin_book",Object[].class);
        List<Object[]> issueCards = issueCardTypedQuery.getResultList();
        issueCards.stream().forEach(objects -> Arrays.asList(issueCards.get(0)).stream().forEach(o -> System.out.println(o.toString())));
        return issueCards.size();
    }

    public int getIssueCardCrossJoinBook(){
        TypedQuery<Object[]> issueCardTypedQuery = entityManager.createNamedQuery("issuecard_crossJoin_book",Object[].class);
        List<Object[]> issueCards = issueCardTypedQuery.getResultList();
        issueCards.stream().forEach(objects -> Arrays.asList(issueCards.get(0)).stream().forEach(o -> System.out.println(o.toString())));
        return issueCards.size();
    }

    public List<IssueCard> getAllIssueCardWithPersonUsingNplus1(){
        EntityGraph<IssueCard> issueCardEntityGraph = entityManager.createEntityGraph(IssueCard.class);
        Subgraph<Person> personSubgraph = issueCardEntityGraph.addSubgraph("person");
        return entityManager
                .createNativeQuery("find_all_issueCard",IssueCard.class)
                .setHint("javax.persistence.loadgraph",issueCardEntityGraph)
                .getResultList();
    }



}
