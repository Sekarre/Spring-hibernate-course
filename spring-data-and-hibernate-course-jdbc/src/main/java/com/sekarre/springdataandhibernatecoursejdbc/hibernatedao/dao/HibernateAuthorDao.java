package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.AuthorHibernateDao;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HibernateAuthorDao {

    List<AuthorHibernateDao> findAllByLastName(String lastName, Pageable pageable);

    List<AuthorHibernateDao> findAll();

    List<AuthorHibernateDao> listAuthorByLastNameLike(String lastName);

    AuthorHibernateDao getById(Long id);

    AuthorHibernateDao findByName(String firstName, String lastName);

    AuthorHibernateDao findByNameCriteria(String firstName, String lastName);

    AuthorHibernateDao findByNameNative(String firstName, String lastName);

    AuthorHibernateDao saveNewAuthor(AuthorHibernateDao author);

    AuthorHibernateDao updateAuthor(AuthorHibernateDao author);

    void deleteAuthorById(Long id);
}
