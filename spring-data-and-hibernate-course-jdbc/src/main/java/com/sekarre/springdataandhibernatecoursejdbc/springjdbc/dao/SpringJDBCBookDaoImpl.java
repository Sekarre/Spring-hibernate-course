package com.sekarre.springdataandhibernatecoursejdbc.springjdbc.dao;

import com.sekarre.springdataandhibernatecoursejdbc.springjdbc.domain.BookSpringJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SpringJDBCBookDaoImpl implements SpringJDBCBookDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_BOOKS_PAGED_STATEMENT = "select * from book limit ? offset ?";
    private static final String GET_ALL_BOOKS_STATEMENT = "select * from book";
    private static final String GET_BOOK_BY_ID_STATEMENT = "select * from book where id = ?";
    private static final String GET_BOOK_BY_TITLE_STATEMENT = "select * from book where title = ?";
    private static final String SAVE_NEW_BOOK_STATEMENT = "insert into book (title, isbn, publisher, author_id) values (?, ?, ?, ?)";
    private static final String UPDATE_BOOK_STATEMENT = "update book set title = ?, isbn = ?, publisher = ?, author_id = ? where book.id = ?";
    private static final String DELETE_BOOK_STATEMENT = "delete from book where id = ?";
    private static final String SELECT_LAST_ID_STATEMENT = "select LAST_INSERT_ID()";

    @Override
    public List<BookSpringJdbc> findAllSortByTitle(Pageable pageable) {
        String sql = "select * from book order by title " +
                pageable.getSort().getOrderFor("title").getDirection().name() + " limit ? offset ?";

        return jdbcTemplate.query(sql, getRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<BookSpringJdbc> findAll(Pageable pageable) {
        return jdbcTemplate.query(GET_ALL_BOOKS_PAGED_STATEMENT, getRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<BookSpringJdbc> findAll(int pageSize, int offset) {
        return jdbcTemplate.query(GET_ALL_BOOKS_PAGED_STATEMENT, getRowMapper(), pageSize, offset);
    }

    @Override
    public List<BookSpringJdbc> findAll() {
        return jdbcTemplate.query(GET_ALL_BOOKS_STATEMENT, getRowMapper());
    }

    @Override
    public BookSpringJdbc getById(Long id) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ID_STATEMENT, getRowMapper(), id);
    }

    @Override
    public BookSpringJdbc findBookByTitle(String title) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_TITLE_STATEMENT, getRowMapper(), title);
    }

    @Override
    public BookSpringJdbc saveNewBook(BookSpringJdbc book) {
        jdbcTemplate.update(SAVE_NEW_BOOK_STATEMENT, book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());
        Long createdId = jdbcTemplate.queryForObject(SELECT_LAST_ID_STATEMENT, Long.class);
        return getById(createdId);
    }

    @Override
    public BookSpringJdbc updateBook(BookSpringJdbc book) {
        jdbcTemplate.update(UPDATE_BOOK_STATEMENT, book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());
        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update(DELETE_BOOK_STATEMENT, id);
    }

    private RowMapper<BookSpringJdbc> getRowMapper() {
        return new BookMapper();
    }
}