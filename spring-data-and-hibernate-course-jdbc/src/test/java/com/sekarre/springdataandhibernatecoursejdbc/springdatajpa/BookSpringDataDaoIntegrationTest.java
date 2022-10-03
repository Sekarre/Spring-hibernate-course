package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao.SpringDataBookDao;
import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.BookSpringData;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookSpringDataDaoIntegrationTest {

    @Autowired
    SpringDataBookDao springDataBookDao;


    @Test
    void testFindAllBooksPageableSorted() {
        List<BookSpringData> books = springDataBookDao.findAllSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPageable() {
        List<BookSpringData> books = springDataBookDao.findAll(PageRequest.of(0, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }


    @Test
    void testGetBookById() {
        BookSpringData book = springDataBookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitle() {
        BookSpringData book = springDataBookDao.findByTitle("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleNotFound() {
        assertThrows(EntityNotFoundException.class, () -> springDataBookDao.findByTitle("1221214123"));
    }

    @Test
    void testSaveBook() {
        BookSpringData book = getBookMock();
        BookSpringData savedBook = springDataBookDao.saveNewBook(book);

        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBook() {
        BookSpringData book = getBookMock();
        BookSpringData savedBook = springDataBookDao.saveNewBook(book);

        String publisher = "Thompson";
        savedBook.setPublisher(publisher);
        BookSpringData updatedBook = springDataBookDao.updateBook(savedBook);

        assertThat(updatedBook.getPublisher()).isEqualTo(publisher);
    }

    @Test
    void testDeleteBook() {
        BookSpringData book = getBookMock();
        BookSpringData savedBook = springDataBookDao.saveNewBook(book);
        springDataBookDao.deleteBookById(savedBook.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> springDataBookDao.getById(savedBook.getId()));
    }


    private static BookSpringData getBookMock() {
        return BookSpringData.builder()
                .title(UUID.randomUUID().toString().substring(0, 8))
                .isbn("123")
                .publisher("Skr")
                .authorId(1L)
                .build();
    }
}
