package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateBookDao;
import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.BookHibernateDao;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookHibernateDaoIntegrationTest {

    @Autowired
    HibernateBookDao hibernateBookDao;

    @Test
    void testFindAllBooksPageableSorted() {
        List<BookHibernateDao> books = hibernateBookDao.findAllSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPageable() {
        List<BookHibernateDao> books = hibernateBookDao.findAll(PageRequest.of(0, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooks() {
        List<BookHibernateDao> books = hibernateBookDao.findAll();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void testFindByIsbn() {
        BookHibernateDao book = BookHibernateDao.builder()
                .isbn("1234" + RandomString.make())
                .title("ISBN TEST")
                .build();

        BookHibernateDao saved = hibernateBookDao.saveNewBook(book);

        BookHibernateDao fetched = hibernateBookDao.findByISBN(saved.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testGetBookById() {
        BookHibernateDao book = hibernateBookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitle() {
        BookHibernateDao book = hibernateBookDao.findByTitle("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleCriteria() {
        BookHibernateDao book = hibernateBookDao.findByTitleCriteria("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleNative() {
        BookHibernateDao book = hibernateBookDao.findByTitleNative("Spring in Action, 6th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBook() {
        BookHibernateDao book = getBookMock();
        BookHibernateDao savedBook = hibernateBookDao.saveNewBook(book);

        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBook() {
        BookHibernateDao book = getBookMock();
        BookHibernateDao savedBook = hibernateBookDao.saveNewBook(book);

        String publisher = "Thompson";
        savedBook.setPublisher(publisher);
        BookHibernateDao updatedBook = hibernateBookDao.updateBook(savedBook);

        assertThat(updatedBook.getPublisher()).isEqualTo(publisher);
    }

    @Test
    void testDeleteBook() {
        BookHibernateDao book = getBookMock();
        BookHibernateDao savedBook = hibernateBookDao.saveNewBook(book);
        hibernateBookDao.deleteBookById(savedBook.getId());

        BookHibernateDao deleted = hibernateBookDao.getById(savedBook.getId());
        assertThat(deleted).isNull();
    }


    private static BookHibernateDao getBookMock() {
        return BookHibernateDao.builder()
                .title(UUID.randomUUID().toString().substring(0, 8))
                .isbn("123")
                .publisher("Skr")
                .authorId(1L)
                .build();
    }
}
