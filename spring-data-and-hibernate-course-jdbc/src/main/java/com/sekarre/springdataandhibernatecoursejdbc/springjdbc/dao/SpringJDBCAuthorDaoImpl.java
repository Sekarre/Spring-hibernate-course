package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.AuthorSpringJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SpringJDBCAuthorDaoImpl implements SpringJDBCAuthorDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_AUTHOR_BY_ID_STATEMENT = "select * from author where id = ?";
    private static final String GET_AUTHOR_BY_ID_WITH_BOOKS_STATEMENT = "select author.id as id, first_name, last_name, book.id as book_id, book.isbn, book.publisher, book.title from author " +
            "left outer join book on author.id = book.author_id where author.id = ?";
    private static final String GET_AUTHOR_BY_NAME_STATEMENT = "select * from author where first_name = ? and last_name = ?";
    private static final String SAVE_NEW_AUTHOR_STATEMENT = "insert into author (first_name, last_name) values (?, ?)";
    private static final String UPDATE_AUTHOR_STATEMENT = "update author set first_name = ?, last_name = ? where author.id = ?";
    private static final String DELETE_AUTHOR_STATEMENT = "delete from author where id = ?";
    private static final String SELECT_LAST_ID_STATEMENT = "select LAST_INSERT_ID()";

    @Override
    public List<AuthorSpringJdbc> findAllByLastName(String lastName, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select * from author where last_name = ? ");

        if (pageable.getSort().getOrderFor("firstname") != null) {
            sb.append(" order by first_name ").append(pageable.getSort().getOrderFor("firstname").getDirection().name());
        }

        sb.append(" limit ? offset ? ");

        return jdbcTemplate.query(sb.toString(), getRowMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public AuthorSpringJdbc getById(Long id) {
        return jdbcTemplate.query(GET_AUTHOR_BY_ID_WITH_BOOKS_STATEMENT, new AuthorExtractor(), id);
    }

    @Override
    public AuthorSpringJdbc findByName(String fistName, String lastName) {
        return jdbcTemplate.queryForObject(GET_AUTHOR_BY_NAME_STATEMENT, getRowMapper(), fistName, lastName);
    }

    @Override
    public AuthorSpringJdbc saveNewAuthor(AuthorSpringJdbc author) {
        jdbcTemplate.update(SAVE_NEW_AUTHOR_STATEMENT, author.getFirstName(), author.getLastName());
        Long createdId = jdbcTemplate.queryForObject(SELECT_LAST_ID_STATEMENT, Long.class);
        return getById(createdId);
    }

    @Override
    public AuthorSpringJdbc updateAuthor(AuthorSpringJdbc author) {
        jdbcTemplate.update(UPDATE_AUTHOR_STATEMENT, author.getFirstName(), author.getLastName(), author.getId());
        return getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update(DELETE_AUTHOR_STATEMENT, id);
    }

    private RowMapper<AuthorSpringJdbc> getRowMapper() {
        return new AuthorMapper();
    }
}
