package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.AuthorSpringJdbc;
import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.BookSpringJdbc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class AuthorMapper implements RowMapper<AuthorSpringJdbc> {

    @Override
    public AuthorSpringJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthorSpringJdbc author = AuthorSpringJdbc.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();

        try {
            if (Objects.nonNull(rs.getString("isbn"))) {
                author.setBooks(new ArrayList<>());
                author.getBooks().add(mapBook(rs));

                while (rs.next()) {
                    author.getBooks().add(mapBook(rs));
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }

        return author;
    }

    private BookSpringJdbc mapBook(ResultSet rs) throws SQLException {
        return BookSpringJdbc.builder()
                .id(rs.getLong("book_id"))
                .title(rs.getString("title"))
                .isbn(rs.getString("isbn"))
                .publisher(rs.getString("publisher"))
                .authorId(rs.getLong("id"))
                .build();
    }
}
