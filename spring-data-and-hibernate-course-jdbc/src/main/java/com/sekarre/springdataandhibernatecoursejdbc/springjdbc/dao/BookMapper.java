package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.BookSpringJdbc;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<BookSpringJdbc> {

    @Override
    public BookSpringJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BookSpringJdbc.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .isbn(rs.getString("isbn"))
                .publisher(rs.getString("publisher"))
                .authorId(rs.getLong("author_id"))
                .build();
    }
}
