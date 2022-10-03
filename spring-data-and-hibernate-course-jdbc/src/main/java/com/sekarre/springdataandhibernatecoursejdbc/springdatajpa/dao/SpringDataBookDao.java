package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.BookSpringData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringDataBookDao {

    List<BookSpringData> findAllSortByTitle(Pageable pageable);

    List<BookSpringData> findAll(Pageable pageable);

    BookSpringData getById(Long id);

    BookSpringData findByTitle(String title);

    BookSpringData saveNewBook(BookSpringData book);

    BookSpringData updateBook(BookSpringData book);

    void deleteBookById(Long id);
}
