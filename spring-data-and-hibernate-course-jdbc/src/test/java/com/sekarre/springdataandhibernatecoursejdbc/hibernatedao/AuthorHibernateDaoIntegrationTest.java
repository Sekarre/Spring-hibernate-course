package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateAuthorDao;
import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.AuthorHibernateDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorHibernateDaoIntegrationTest {

    @Autowired
    HibernateAuthorDao hibernateAuthorDao;

    @Test
    void testFindAllByLastName_pageable() {
        List<AuthorHibernateDao> authors = hibernateAuthorDao.findAllByLastName("Walls", PageRequest.of(0, 10, Sort.by("firstname")));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testFindAllAuthors() {
        List<AuthorHibernateDao> authors = hibernateAuthorDao.findAll();

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testListAuthorByLastNameLike() {
        List<AuthorHibernateDao> authors = hibernateAuthorDao.listAuthorByLastNameLike("Wall");

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testGetAuthorById() {
        AuthorHibernateDao author = hibernateAuthorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        AuthorHibernateDao author = hibernateAuthorDao.findByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByNameCriteria() {
        AuthorHibernateDao author = hibernateAuthorDao.findByNameCriteria("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByNameNative() {
        AuthorHibernateDao author = hibernateAuthorDao.findByNameNative("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor() {
        AuthorHibernateDao author = AuthorHibernateDao.builder()
                .firstName("John")
                .lastName("T2")
                .build();
        AuthorHibernateDao savedAuthor = hibernateAuthorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        AuthorHibernateDao author = AuthorHibernateDao.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorHibernateDao savedAuthor = hibernateAuthorDao.saveNewAuthor(author);

        String lastName = "Thompson";
        savedAuthor.setLastName(lastName);
        AuthorHibernateDao updatedAuthor = hibernateAuthorDao.updateAuthor(savedAuthor);

        assertThat(updatedAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testDeleteAuthor() {
        AuthorHibernateDao author = AuthorHibernateDao.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorHibernateDao savedAuthor = hibernateAuthorDao.saveNewAuthor(author);
        hibernateAuthorDao.deleteAuthorById(savedAuthor.getId());

        AuthorHibernateDao deleted = hibernateAuthorDao.getById(savedAuthor.getId());
        assertThat(deleted).isNull();
    }
}
