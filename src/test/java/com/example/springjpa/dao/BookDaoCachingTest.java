package com.example.springjpa.dao;

import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  SpringJpaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BookDaoCachingTest {

    @Autowired
    BookDao bookDao;;

    @Test
    public void firstLevelCahceDaoTransactional() throws NotFoundException {
        Book book = new Book("Learn Java","Author1","Programming",1);
        int bookid = bookDao.insetBook(book);
        Assert.assertNotNull("Run Query and take data from DB due to dao transactional boundary",bookDao.findById(bookid));
        Assert.assertNotNull("Run Query and take data from DB due to dao transactional boundary",bookDao.findById(bookid));
    }

    @Test
    @Transactional
    public void firstLevelCahceDao() throws NotFoundException {
        Book book = new Book("Learn Java","Author1","Programming",1);
        int bookid = bookDao.insetBook(book);
        Assert.assertNotNull("Run Query and take data from DB due to dao transactional boundary",bookDao.findById(bookid));
        Assert.assertNotNull("Do not take data from db as method is in transactional",bookDao.findById(bookid));
    }
}
