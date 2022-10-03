package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.AuthorSpringJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<AuthorSpringJdbc> {

    @Override
    public AuthorSpringJdbc extractData(ResultSet rs) throws SQLException, DataAccessException {
        rs.next();
        return new AuthorMapper().mapRow(rs, 0);
    }
}
