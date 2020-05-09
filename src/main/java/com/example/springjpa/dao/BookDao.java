package com.example.springjpa.dao;

import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Book;
import com.example.springjpa.model.Person;
import com.example.springjpa.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookDao {

    private Logger logger = LoggerFactory.getLogger(BookDao.class);
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public  void setProp(){
        entityManager.setProperty("javax.persistence.sharedCache.mode","ENABLE_SELECTIVE");
    }

    @Transactional
    public Book findById(int id) throws NotFoundException {

        Book book =entityManager.find(Book.class,id);
        if(book!=null){
            return book;
        }
        throw  new NotFoundException("Data not found for id "+id);
    }

    public Book updateBook(Book book){
        Book book1 =  entityManager.merge(book);
        logger.info("Upadted Data {} has been inserted to DB", book1.toString());
        return book1;
    }

    @Transactional
    public int  insetBook(Book book){
        entityManager.persist(book);
        entityManager.flush();
        logger.info("Data {} has been inserted to DB", book.toString());
        return book.getId();
    }

    public void deleteBook(int id) throws NotFoundException{
        Book book = this.findById(id);
        entityManager.remove(book);
        logger.info("Data {} has been deleted from DB", book.toString());
    }

    public List<Book> getAllBook(){
        TypedQuery<Book> bookTypedQuery = entityManager.createNamedQuery("find_all_book",Book.class);
        List<Book> books = bookTypedQuery.getResultList();
        Util.printRow(books);
        return books;
    }

    public List<Book> getAllNonIssuedBook(){
        TypedQuery<Book> bookTypedQuery = entityManager.createNamedQuery("find_all_book_not_issued",Book.class);
        List<Book> books = bookTypedQuery.getResultList();
        Util.printRow(books);
        return books;
    }


}
