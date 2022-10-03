package com.sekarre.springdataandhibernatecoursejdbc.jdbc;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao.JDBCBookDao;
import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.AuthorJdbc;
import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.BookJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookJdbcDaoIntegrationTest {

    @Autowired
    JDBCBookDao JDBCBookDao;

    @Test
    void testGetBookById() {
        BookJdbc book = JDBCBookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByName() {
        BookJdbc book = JDBCBookDao.findBookByTitle("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBook() {
        BookJdbc book = getBookMock();
        BookJdbc savedBook = JDBCBookDao.saveNewBook(book);

        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBook() {
        BookJdbc book = getBookMock();
        BookJdbc savedBook = JDBCBookDao.saveNewBook(book);

        String publisher = "Thompson";
        savedBook.setPublisher(publisher);
        BookJdbc updatedBook = JDBCBookDao.updateBook(savedBook);

        assertThat(updatedBook.getPublisher()).isEqualTo(publisher);
    }

    @Test
    void testDeleteBook() {
        BookJdbc book = getBookMock();
        BookJdbc savedBook = JDBCBookDao.saveNewBook(book);
        JDBCBookDao.deleteBookById(savedBook.getId());

        BookJdbc deleted = JDBCBookDao.getById(savedBook.getId());

        assertThat(deleted).isNull();
    }


    private static BookJdbc getBookMock() {
        return BookJdbc.builder()
                .title(UUID.randomUUID().toString().substring(0, 8))
                .isbn("123")
                .publisher("Skr")
                .author(AuthorJdbc.builder().id(1L).build())
                .build();
    }
}
