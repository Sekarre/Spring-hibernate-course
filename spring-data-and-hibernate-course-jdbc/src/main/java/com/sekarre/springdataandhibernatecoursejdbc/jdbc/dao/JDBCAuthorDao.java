package com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.AuthorJdbc;

public interface JDBCAuthorDao {

    AuthorJdbc getById(Long id);

    AuthorJdbc findByName(String fistName, String lastName);

    AuthorJdbc saveNewAuthor(AuthorJdbc author);

    AuthorJdbc updateAuthor(AuthorJdbc author);

    void deleteAuthorById(Long id);
}
