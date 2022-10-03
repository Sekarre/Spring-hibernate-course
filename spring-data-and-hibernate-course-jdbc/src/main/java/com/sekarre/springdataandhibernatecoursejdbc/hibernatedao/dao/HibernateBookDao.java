package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.BookHibernateDao;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HibernateBookDao {

    List<BookHibernateDao> findAllSortByTitle(Pageable pageable);

    List<BookHibernateDao> findAll(Pageable pageable);

    List<BookHibernateDao> findAll();

    BookHibernateDao findByISBN(String isbn);

    BookHibernateDao getById(Long id);

    BookHibernateDao findByTitle(String title);

    BookHibernateDao findByTitleCriteria(String title);

    BookHibernateDao findByTitleNative(String title);

    BookHibernateDao saveNewBook(BookHibernateDao book);

    BookHibernateDao updateBook(BookHibernateDao book);

    void deleteBookById(Long id);
}
