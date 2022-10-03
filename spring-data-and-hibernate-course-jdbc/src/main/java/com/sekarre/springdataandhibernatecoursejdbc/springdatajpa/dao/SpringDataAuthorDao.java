package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.AuthorSpringData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringDataAuthorDao {

    List<AuthorSpringData> findAllByLastName(String lastName, Pageable pageable);

    AuthorSpringData getById(Long id);

    AuthorSpringData findByName(String firstName, String lastName);

    AuthorSpringData saveNewAuthor(AuthorSpringData author);

    AuthorSpringData updateAuthor(AuthorSpringData author);

    void deleteAuthorById(Long id);
}
