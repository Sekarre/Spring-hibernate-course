package com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.AuthorJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@RequiredArgsConstructor
@Component
public class JDBCAuthorDaoImpl implements JDBCAuthorDao {

    private final DataSource dataSource;

    private static final String GET_AUTHOR_BY_ID_STATEMENT = "select * from author where id = ?";
    private static final String GET_AUTHOR_BY_NAME_STATEMENT = "select * from author where first_name = ? and last_name = ?";
    private static final String SAVE_NEW_AUTHOR_STATEMENT = "insert into author (first_name, last_name) values (?, ?)";
    private static final String UPDATE_AUTHOR_STATEMENT = "update author set first_name = ?, last_name = ? where author.id = ?";
    private static final String DELETE_AUTHOR_STATEMENT = "delete from author where id = ?";
    private static final String SELECT_LAST_ID_STATEMENT = "select LAST_INSERT_ID()";

    @Override
    public AuthorJdbc getById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_AUTHOR_BY_ID_STATEMENT)) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getAuthorFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public AuthorJdbc findByName(String fistName, String lastName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_AUTHOR_BY_NAME_STATEMENT)) {
            ps.setString(1, fistName);
            ps.setString(2, lastName);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getAuthorFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public AuthorJdbc saveNewAuthor(AuthorJdbc author) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(SAVE_NEW_AUTHOR_STATEMENT)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();
            try (ResultSet resultSet = statement.executeQuery(SELECT_LAST_ID_STATEMENT)) {
                if (resultSet.next()) {
                    Long savedId = resultSet.getLong(1);
                    return getById(savedId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public AuthorJdbc updateAuthor(AuthorJdbc author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_AUTHOR_STATEMENT)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setLong(3, author.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_AUTHOR_STATEMENT)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthorJdbc getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        return AuthorJdbc.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .build();
    }
}
