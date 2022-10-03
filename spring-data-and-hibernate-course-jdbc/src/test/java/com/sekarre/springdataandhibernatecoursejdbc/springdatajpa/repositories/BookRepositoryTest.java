package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.repositories;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.BookSpringData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testBookJPANamedQuery() {
        BookSpringData book = bookRepository.bookByTitleJpaNamed("Clean code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookNativeQuery() {
        BookSpringData book = bookRepository.findBookByTitleNativeQuery("Clean code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookQueryName() {
        BookSpringData book = bookRepository.findBookByTitleWithQueryNamed("Clean code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookQuery() {
        BookSpringData book = bookRepository.findBookByTitleWithQuery("Clean code");
        assertThat(book).isNotNull();
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException() {
        assertNull(bookRepository.getByTitle("foo"));
    }

    @Test
    void testBookStream() {
        AtomicInteger count = new AtomicInteger();
        bookRepository.findAllByTitleNotNull().forEach(book -> count.incrementAndGet());
        assertThat(count.get()).isGreaterThan(5);
    }

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<BookSpringData> bookFuture = bookRepository.queryByTitle("Clean code");
        BookSpringData book = bookFuture.get();
        assertNotNull(book);
    }
}
