package com.example.springjpa.dao;


import com.example.springjpa.SpringJpaApplication;
import com.example.springjpa.exception.NotFoundException;
import com.example.springjpa.model.Book;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes =  SpringJpaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    private int bookid = 0;
    @Before
    public void setUp(){
        Book book = new Book("Learn Java","Author1","Programming",1);
        this.bookid = bookDao.insetBook(book);
    }

    @After
    public void cleanUp(){
        try {
            bookDao.deleteBook(this.bookid);
        }
        catch (Exception e){}
    }

    @Test
    public void insetBook() {
        Book book = new Book("Learn Python","dhj j sj","Programming",1);
        bookDao.insetBook(book);
        Assert.assertTrue(true);
    }

    @Test
    public void findById() throws NotFoundException {
        Assert.assertEquals("Learn Java",bookDao.findById(this.bookid).getBookName());
    }

    @Test
    public void updateBook() {
        Book book = new Book("Learn Python 2","dhj j sj","Programming",2);
        book.setId(this.bookid);
        Assert.assertEquals(bookDao.updateBook(book).getBookName(),"Learn Python 2");
    }


    @Test
    public void deleteBook() throws NotFoundException {
        bookDao.deleteBook(this.bookid);
        Assert.assertEquals(0,bookDao.getAllBook().size());
    }

    @Test
    public void getAllBook() {
        Assert.assertEquals(1,bookDao.getAllBook().size());
    }


}