package com.sekarre.springdataandhibernatecoursejdbc.jdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.jdbc.domain.BookJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JDBCBookDaoImpl implements JDBCBookDao {

    private final DataSource dataSource;
    private final JDBCAuthorDao JDBCAuthorDao;

    private static final String GET_BOOK_BY_ID_STATEMENT = "select * from book where id = ?";
    private static final String GET_BOOK_BY_TITLE_STATEMENT = "select * from book where title = ?";
    private static final String SAVE_NEW_BOOK_STATEMENT = "insert into book (title, isbn, publisher, author_id) values (?, ?, ?, ?)";
    private static final String UPDATE_BOOK_STATEMENT = "update book set title = ?, isbn = ?, publisher = ?, author_id = ? where book.id = ?";
    private static final String DELETE_BOOK_STATEMENT = "delete from book where id = ?";
    private static final String SELECT_LAST_ID_STATEMENT = "select LAST_INSERT_ID()";

    @Override
    public BookJdbc getById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID_STATEMENT)) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public BookJdbc findBookByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_TITLE_STATEMENT)) {
            ps.setString(1, title);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public BookJdbc saveNewBook(BookJdbc book) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(SAVE_NEW_BOOK_STATEMENT)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());

            if (Objects.nonNull(book.getAuthor())) {
                ps.setLong(4, book.getAuthor().getId());
            } else {
                ps.setNull(4, -5);
            }

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
    public BookJdbc updateBook(BookJdbc book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK_STATEMENT)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());
            if (Objects.nonNull(book.getAuthor())) {
                ps.setLong(4, book.getAuthor().getId());
            }
            ps.setLong(5, book.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOK_STATEMENT)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private BookJdbc getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return BookJdbc.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .isbn(resultSet.getString("isbn"))
                .publisher(resultSet.getString("publisher"))
                .author(JDBCAuthorDao.getById(resultSet.getLong("author_id")))
                .build();
    }
}