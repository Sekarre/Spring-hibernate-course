package com.sekarre.springdataandhibernatecoursejdbc.jdbc;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao.JDBCAuthorDao;
import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.AuthorJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao"})
//@Import(value = AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorJdbcDaoIntegrationTest {

    @Autowired
    JDBCAuthorDao JDBCAuthorDao;

    @Test
    void testGetAuthorById() {
        AuthorJdbc author = JDBCAuthorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        AuthorJdbc author = JDBCAuthorDao.findByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor() {
        AuthorJdbc author = AuthorJdbc.builder()
                .firstName("John")
                .lastName("Thompson")
                .build();
        AuthorJdbc savedAuthor = JDBCAuthorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        AuthorJdbc author = AuthorJdbc.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorJdbc savedAuthor = JDBCAuthorDao.saveNewAuthor(author);

        String lastName = "Thompson";
        savedAuthor.setLastName(lastName);
        AuthorJdbc updatedAuthor = JDBCAuthorDao.updateAuthor(savedAuthor);

        assertThat(updatedAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testDeleteAuthor() {
        AuthorJdbc author = AuthorJdbc.builder()
                .firstName("John")
                .lastName("T")
                .build();
        AuthorJdbc savedAuthor = JDBCAuthorDao.saveNewAuthor(author);
        JDBCAuthorDao.deleteAuthorById(savedAuthor.getId());

        AuthorJdbc deleted = JDBCAuthorDao.getById(savedAuthor.getId());

        assertThat(deleted).isNull();
    }
}
