package com.sekarre.springdataandhibernatecoursejdbc.springjdbc;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao.SpringJDBCAuthorDao;
import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.AuthorSpringJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorSpringJdbcDaoIntegrationTest {

    @Autowired
    SpringJDBCAuthorDao springJDBCAuthorDao;

    @Test
    void testFindAllByLastName_pageable() {
        List<AuthorSpringJdbc> authors = springJDBCAuthorDao.findAllByLastName("Walls", PageRequest.of(0, 10, Sort.by("firstname")));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testGetAuthorById() {
        AuthorSpringJdbc author = springJDBCAuthorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        AuthorSpringJdbc author = springJDBCAuthorDao.findByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor() {
        AuthorSpringJdbc author = AuthorSpringJdbc.builder()
                .firstName("John")
                .lastName("T2")
                .build();
        AuthorSpringJdbc savedAuthor = springJDBCAuthorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        AuthorSpringJdbc author = AuthorSpringJdbc.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorSpringJdbc savedAuthor = springJDBCAuthorDao.saveNewAuthor(author);

        String lastName = "Thompson";
        savedAuthor.setLastName(lastName);
        AuthorSpringJdbc updatedAuthor = springJDBCAuthorDao.updateAuthor(savedAuthor);

        assertThat(updatedAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testDeleteAuthor() {
        AuthorSpringJdbc author = AuthorSpringJdbc.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorSpringJdbc savedAuthor = springJDBCAuthorDao.saveNewAuthor(author);
        springJDBCAuthorDao.deleteAuthorById(savedAuthor.getId());

        assertThrows(TransientDataAccessResourceException.class, () -> springJDBCAuthorDao.getById(savedAuthor.getId()));
    }
}
