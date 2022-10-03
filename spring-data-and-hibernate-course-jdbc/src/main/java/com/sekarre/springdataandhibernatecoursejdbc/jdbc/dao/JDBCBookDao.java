package com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.BookJdbc;

public interface JDBCBookDao {

    BookJdbc getById(Long id);

    BookJdbc findBookByTitle(String title);

    BookJdbc saveNewBook(BookJdbc book);

    BookJdbc updateBook(BookJdbc book);

    void deleteBookById(Long id);
}
