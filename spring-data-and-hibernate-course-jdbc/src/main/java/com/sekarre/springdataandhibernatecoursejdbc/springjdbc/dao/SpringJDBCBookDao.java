package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.BookSpringJdbc;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringJDBCBookDao {

    List<BookSpringJdbc> findAllSortByTitle(Pageable pageable);

    List<BookSpringJdbc> findAll(Pageable pageable);

    List<BookSpringJdbc> findAll(int pageSize, int offset);

    List<BookSpringJdbc> findAll();

    BookSpringJdbc getById(Long id);

    BookSpringJdbc findBookByTitle(String title);

    BookSpringJdbc saveNewBook(BookSpringJdbc book);

    BookSpringJdbc updateBook(BookSpringJdbc book);

    void deleteBookById(Long id);
}
