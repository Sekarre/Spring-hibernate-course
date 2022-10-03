package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.AuthorSpringJdbc;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringJDBCAuthorDao {

    List<AuthorSpringJdbc> findAllByLastName(String lastName, Pageable pageable);

    AuthorSpringJdbc getById(Long id);

    AuthorSpringJdbc findByName(String fistName, String lastName);

    AuthorSpringJdbc saveNewAuthor(AuthorSpringJdbc author);

    AuthorSpringJdbc updateAuthor(AuthorSpringJdbc author);

    void deleteAuthorById(Long id);
}
