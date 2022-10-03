package com.sekarre.springdataandhibernatecoursejdbc.springdatajpa;

import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao.SpringDataAuthorDao;
import com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.domain.AuthorSpringData;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorSpringDataDaoIntegrationTest {

    @Autowired
    SpringDataAuthorDao springDataAuthorDao;


    @Test
    void testFindAllByLastName() {
        List<AuthorSpringData> authors = springDataAuthorDao.findAllByLastName("Walls", PageRequest.of(0, 10, Sort.by("firstName")));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testGetAuthorById() {
        AuthorSpringData author = springDataAuthorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        AuthorSpringData author = springDataAuthorDao.findByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByNameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> springDataAuthorDao.findByName("123", "123"));
    }


    @Test
    void testSaveAuthor() {
        AuthorSpringData author = AuthorSpringData.builder()
                .firstName("John")
                .lastName("T2")
                .build();
        AuthorSpringData savedAuthor = springDataAuthorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        AuthorSpringData author = AuthorSpringData.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorSpringData savedAuthor = springDataAuthorDao.saveNewAuthor(author);

        String lastName = "Thompson";
        savedAuthor.setLastName(lastName);
        AuthorSpringData updatedAuthor = springDataAuthorDao.updateAuthor(savedAuthor);

        assertThat(updatedAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testDeleteAuthor() {
        AuthorSpringData author = AuthorSpringData.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorSpringData savedAuthor = springDataAuthorDao.saveNewAuthor(author);
        springDataAuthorDao.deleteAuthorById(savedAuthor.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> springDataAuthorDao.getById(savedAuthor.getId()));
    }
}
