package com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateAuthorDaoImpl.AUTHOR_BY_NAME_QUERY_NAME;
import static com.sekarre.springdataandhibernatecoursejdbc.hibernatedao.dao.HibernateAuthorDaoImpl.AUTHOR_FIND_ALL_QUERY_NAME;

@NamedQueries({
        @NamedQuery(name = AUTHOR_FIND_ALL_QUERY_NAME, query = "from AuthorHibernateDao"),
        @NamedQuery(name = AUTHOR_BY_NAME_QUERY_NAME, query = "from AuthorHibernateDao a where a.firstName = :firstName and a.lastName = :lastName")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "author")
public class AuthorHibernateDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Transient
    private List<BookHibernateDao> books;
}
