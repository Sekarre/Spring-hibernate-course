package com.sekarre.springdataandhibernatecoursejdbc.springjdbc;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao.SpringJDBCBookDao;
import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.BookSpringJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookSpringJdbcDaoIntegrationTest {

    @Autowired
    SpringJDBCBookDao springJDBCBookDao;

    @Test
    void testFindAllBooksSortByTitle() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAllSortByTitle(PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage1_pageable() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(PageRequest.of(0, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2_pageable() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(PageRequest.of(1, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage10_pageable() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(PageRequest.of(10, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooksPage1() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(10, 0);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(10, 10);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage10() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll(10, 100);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooks() {
        List<BookSpringJdbc> books = springJDBCBookDao.findAll();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(5);
    }

    @Test
    void testGetBookById() {
        BookSpringJdbc book = springJDBCBookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByName() {
        BookSpringJdbc book = springJDBCBookDao.findBookByTitle("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBook() {
        BookSpringJdbc book = getBookMock();
        BookSpringJdbc savedBook = springJDBCBookDao.saveNewBook(book);

        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBook() {
        BookSpringJdbc book = getBookMock();
        BookSpringJdbc savedBook = springJDBCBookDao.saveNewBook(book);

        String publisher = "Thompson";
        savedBook.setPublisher(publisher);
        BookSpringJdbc updatedBook = springJDBCBookDao.updateBook(savedBook);

        assertThat(updatedBook.getPublisher()).isEqualTo(publisher);
    }

    @Test
    void testDeleteBook() {
        BookSpringJdbc book = getBookMock();
        BookSpringJdbc savedBook = springJDBCBookDao.saveNewBook(book);
        springJDBCBookDao.deleteBookById(savedBook.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> springJDBCBookDao.getById(savedBook.getId()));
    }


    private static BookSpringJdbc getBookMock() {
        return BookSpringJdbc.builder()
                .title(UUID.randomUUID().toString().substring(0, 8))
                .isbn("123")
                .publisher("Skr")
                .authorId(1L)
                .build();
    }
}
