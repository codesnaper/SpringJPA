package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"issueCard"})
@Entity
@Table(name = "book")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_book",query = "select b from Book b"),
        @NamedQuery(name = "find_all_book_not_issued",query = "select b from Book b where b.issueCard is empty")
})
//@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book {

    public Book() {
    }

    public Book(String bookName, String authorName, String categoryType, int volume) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.categoryType = categoryType;
        this.volume = volume;
    }

    @Id
    @GeneratedValue
    @Column( name = "id",nullable = false)
    private int id;

    @Column(name = "book_name",nullable = false)
    private String bookName;

    @Column(name = "author_name",nullable = false)
    private String authorName;

    @Column(name = "category_type",nullable = false)
    private String categoryType;

    @Column(name = "book_register_date",nullable = false)
    @CreationTimestamp
    private Date registerBookDate;

    private int volume;

    @OneToOne(mappedBy = "book")
    private IssueCard issueCard;

}
