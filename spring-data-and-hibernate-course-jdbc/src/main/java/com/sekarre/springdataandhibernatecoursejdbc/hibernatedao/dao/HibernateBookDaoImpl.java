package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.BookHibernateDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HibernateBookDaoImpl implements HibernateBookDao {

    private final EntityManagerFactory entityManagerFactory;

    public static final String BOOK_FIND_ALL_QUERY_NAME = "book_find_all";
    public static final String BOOK_BY_TITLE_QUERY_NAME = "book_find_by_title";

    private static final String GET_ALL_BOOKS_STATEMENT = "select b from BookHibernateDao b";
    private static final String GET_ALL_BOOKS_ORDERED_STATEMENT = "select b from BookHibernateDao b order by b.title ";
    private static final String GET_BOOK_BY_TITLE_STATEMENT = "select b from BookHibernateDao b where b.title = :title";
    private static final String GET_BOOK_BY_TITLE_NATIVE_STATEMENT = "select * from book b where b.title = :title";
    private static final String GET_BOOK_BY_ISBN_STATEMENT = "select b from BookHibernateDao b where b.isbn = :isbn";

    @Override
    public List<BookHibernateDao> findAllSortByTitle(Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BookHibernateDao> query = em.createQuery(
                    GET_ALL_BOOKS_ORDERED_STATEMENT + pageable.getSort().getOrderFor("title").getDirection().name(),
                    BookHibernateDao.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<BookHibernateDao> findAll(Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BookHibernateDao> query = em.createQuery(GET_ALL_BOOKS_STATEMENT, BookHibernateDao.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<BookHibernateDao> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BookHibernateDao> query = em.createNamedQuery(BOOK_FIND_ALL_QUERY_NAME, BookHibernateDao.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public BookHibernateDao findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BookHibernateDao> query = em.createQuery(GET_BOOK_BY_ISBN_STATEMENT, BookHibernateDao.class);
            query.setParameter("isbn", isbn );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public BookHibernateDao getById(Long id) {
        EntityManager em = getEntityManager();
        BookHibernateDao book = getEntityManager().find(BookHibernateDao.class, id);
        em.close();

        return book;
    }

    @Override
    public BookHibernateDao findByTitle(String title) {
        EntityManager em = getEntityManager();
        try {
//            TypedQuery<BookHibernateDao> query = em.createQuery(GET_BOOK_BY_TITLE_STATEMENT, BookHibernateDao.class);
            TypedQuery<BookHibernateDao> query = em.createNamedQuery(BOOK_BY_TITLE_QUERY_NAME, BookHibernateDao.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public BookHibernateDao findByTitleCriteria(String title) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<BookHibernateDao> criteriaQuery = criteriaBuilder.createQuery(BookHibernateDao.class);

            Root<BookHibernateDao> root = criteriaQuery.from(BookHibernateDao.class);

            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);

            Predicate titlePred = criteriaBuilder.equal(root.get("title"), titleParam);

            criteriaQuery.select(root).where(titlePred);

            TypedQuery<BookHibernateDao> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(titleParam, title);

            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public BookHibernateDao findByTitleNative(String title) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_BOOK_BY_TITLE_NATIVE_STATEMENT, BookHibernateDao.class);
            query.setParameter("title", title);
            return (BookHibernateDao) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public BookHibernateDao saveNewBook(BookHibernateDao book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public BookHibernateDao updateBook(BookHibernateDao book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        BookHibernateDao book = getById(id);
        em.remove(em.contains(book) ? book : em.merge(book));
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}