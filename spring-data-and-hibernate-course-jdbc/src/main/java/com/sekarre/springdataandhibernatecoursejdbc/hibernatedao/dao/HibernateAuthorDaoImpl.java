package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao;

import com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain.AuthorHibernateDao;
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
public class HibernateAuthorDaoImpl implements HibernateAuthorDao {

    private final EntityManagerFactory entityManagerFactory;
    public static final String AUTHOR_FIND_ALL_QUERY_NAME = "author_find_all";
    public static final String AUTHOR_BY_NAME_QUERY_NAME = "author_find_by_name";

    private static final String GET_AUTHOR_BY_NAME_STATEMENT = "select a from AuthorHibernateDao a where a.firstName = :firstName and a.lastName = :lastName";

    private static final String GET_AUTHOR_BY_NAME_NATIVE_STATEMENT = "select * from author a where a.first_name = ? and a.last_name = ?";
    private static final String GET_AUTHOR_BY_LASTNAME_LIKE_STATEMENT = "select a from AuthorHibernateDao a where a.lastName like :lastName";


    @Override
    public List<AuthorHibernateDao> findAllByLastName(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            String hql = " select a from AuthorHibernateDao a where a.lastName = :lastName ";
            if (pageable.getSort().getOrderFor("firstname") != null) {
                hql += " order by a.firstName " + pageable.getSort().getOrderFor("firstname").getDirection().name();
            }
            TypedQuery<AuthorHibernateDao> query = em.createQuery(hql, AuthorHibernateDao.class);
            query.setParameter("lastName", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuthorHibernateDao> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<AuthorHibernateDao> query = em.createNamedQuery(AUTHOR_FIND_ALL_QUERY_NAME, AuthorHibernateDao.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuthorHibernateDao> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<AuthorHibernateDao> query = em.createQuery(GET_AUTHOR_BY_LASTNAME_LIKE_STATEMENT, AuthorHibernateDao.class);
            query.setParameter("lastName", lastName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public AuthorHibernateDao getById(Long id) {
        EntityManager em = getEntityManager();
        AuthorHibernateDao author = getEntityManager().find(AuthorHibernateDao.class, id);
        em.close();

        return author;
    }

    @Override
    public AuthorHibernateDao findByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
//        TypedQuery<AuthorHibernateDao> query = em.createQuery(GET_AUTHOR_BY_NAME_STATEMENT, AuthorHibernateDao.class);
        TypedQuery<AuthorHibernateDao> query = em.createNamedQuery(AUTHOR_BY_NAME_QUERY_NAME, AuthorHibernateDao.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        AuthorHibernateDao author = query.getSingleResult();
        em.close();
        return author;
    }

    @Override
    public AuthorHibernateDao findByNameCriteria(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AuthorHibernateDao> criteriaQuery = criteriaBuilder.createQuery(AuthorHibernateDao.class);

            Root<AuthorHibernateDao> root = criteriaQuery.from(AuthorHibernateDao.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<AuthorHibernateDao> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public AuthorHibernateDao findByNameNative(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_AUTHOR_BY_NAME_NATIVE_STATEMENT, AuthorHibernateDao.class);
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);

            return (AuthorHibernateDao) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public AuthorHibernateDao saveNewAuthor(AuthorHibernateDao author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return author;
    }

    @Override
    public AuthorHibernateDao updateAuthor(AuthorHibernateDao author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(author);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AuthorHibernateDao author = getById(id);
        em.remove(em.contains(author) ? author : em.merge(author));
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
